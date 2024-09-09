from flask import Flask, request, jsonify
import pandas as pd
import joblib

app = Flask(__name__)

# 모델 파일 로드
models = {
    'diabetes_model': joblib.load('diabetes_model.pkl'),
    'heart_disease_model': joblib.load('heart_disease_model.pkl'),
    'hypertension_model': joblib.load('hypertension_model.pkl'),
    'kidney_disease_model': joblib.load('kidney_disease_model.pkl'),
    'obesity_model': joblib.load('obesity_model.pkl'),
    'stroke_prediction_model': joblib.load('stroke_prediction_model.pkl'),
}

# 모델별 입력 데이터 컬럼 정의
model_columns = {
    'diabetes_model': ['age', 'waist', 'BMI', 'systolic blood pressure', 'γ-GTP', 'ALT'],
    'heart_disease_model': ['systolic blood pressure', 'diastolic blood pressure', 'FBG', 'creatinine', 'hemoglobin',
                            'waist', 'BMI', 'smoke', 'drink'],
    'hypertension_model': ['age', 'waist', 'BMI', 'systolic blood pressure', 'diastolic blood pressure'],
    'kidney_disease_model': ['creatinine', 'hemoglobin', 'FBG', 'smoke'],
    'obesity_model': ['waist', 'BMI', 'systolic blood pressure', 'diastolic blood pressure', 'ALT'],
    'stroke_prediction_model': ['age', 'waist', 'systolic blood pressure', 'diastolic blood pressure', 'FBG', 'hemoglobin', 'creatinine', 'smoke', 'drink', 'BMI']
}

def predict(model, data, columns):
    df = pd.DataFrame(data, index=[0])
    # 컬럼 순서를 맞추고 누락된 컬럼을 0으로 채움
    df = df.reindex(columns=columns, fill_value=0)
    try:
        prob = model.predict_proba(df)
        return {
            'probability': {
                'disease': round(prob[0][1] * 100, 1)  # 소수점 한 자리까지 반올림
            }
        }
    except Exception as e:
        return {'error': str(e)}

@app.route('/predict', methods=['POST'])
def predict_route():
    try:
        request_data = request.json
        model_requests = request_data.get('data')
        results = {}

        for model_name, data in model_requests.items():
            if model_name not in models:
                results[model_name] = {'error': 'Invalid model name'}
                continue

            if model_name not in model_columns:
                results[model_name] = {'error': 'No column definitions for this model'}
                continue

            columns = model_columns[model_name]
            result = predict(models[model_name], data, columns)
            results[model_name] = result

        return jsonify(results)

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
