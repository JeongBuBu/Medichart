import React, { useState } from "react";
import "../pages/Useagree.css";

function Useagree({ isOpen, onClose }) {
  const [agreeAll, setAgreeAll] = useState(false);
  const [agreeTerms, setAgreeTerms] = useState(false); // 이용약관 동의
  const [agreePrivacy, setAgreePrivacy] = useState(false); // 개인정보 동의

  const handleAgreeAllChange = () => {
    const newAgreeAll = !agreeAll;
    setAgreeAll(newAgreeAll);
    setAgreeTerms(newAgreeAll);
    setAgreePrivacy(newAgreeAll);
  };

  const handleAgreeTermsChange = () => {
    const newAgreeTerms = !agreeTerms;
    setAgreeTerms(newAgreeTerms);
    setAgreeAll(newAgreeTerms && agreePrivacy);
  };

  const handleAgreePrivacyChange = () => {
    const newAgreePrivacy = !agreePrivacy;
    setAgreePrivacy(newAgreePrivacy);
    setAgreeAll(agreeTerms && newAgreePrivacy);
  };

  // 백엔드로 데이터 전송
  const handleSubmit = async (event) => {
    event.preventDefault();
    if (agreeTerms && agreePrivacy) {
      // 추가 회원가입 처리 로직
      try {
        const response = await fetch("https://api.example.com/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            agreeTerms,
            agreePrivacy,
          }),
        });

        if (response.ok) {
          alert("회원가입 완료");
          onClose(); // 모달 닫기
        } else {
          alert("회원가입에 실패했습니다. 다시 시도해주세요.");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    } else {
      alert("모든 필수 약관에 동의해야 합니다.");
    }
  };

  if (!isOpen) return null;

  return (
      <div className="modal-overlay">
        <div className="modal">
          <div className="modal-content">
            <h2>Medichart 이용약관</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>
                  <input
                      type="checkbox"
                      checked={agreeAll}
                      onChange={handleAgreeAllChange}
                  />
                  전체 동의
                </label>
              </div>
              <div className="form-group">
                <label htmlFor="terms">이용약관</label>
                <textarea
                    id="terms"
                    readOnly
                    value={`  제1조 (목적)
  본 약관은 Medichart(이하 '회사')가 제공하는 건강 진단서 해석 및 질병 예측 서비스(이하 '서비스')의 이용조건 및 절차, 회원과 회사의 권리와 의무, 책임사항 등을 규정함을 목적으로 합니다.
  제2조 (정의)
  1. "회원"이라 함은 본 약관에 동의하고 회사와 서비스 이용 계약을 체결한 자를 말합니다.
  2. "회사"라 함은 Medichart를 운영하는 법인을 말합니다.
  3. "서비스"라 함은 회사가 제공하는 건강 진단서 해석 및 질병 예측 서비스를 말합니다.

  제3조 (약관의 명시, 효력 및 변경)
  1. 회사는 본 약관의 내용을 회원이 쉽게 알 수 있도록 서비스 초기 화면에 게시합니다.
  2. 회사는 약관을 변경할 수 있으며, 변경된 약관은 게시와 동시에 효력이 발생합니다. 변경된 약관의 효력 발생 후 회원이 서비스를 계속 이용하면, 변경된 약관에 동의한 것으로 간주합니다.
  제4조 (회원가입)
  1. 회원가입은 서비스 이용자가 약관 내용에 동의하고, 회사가 정한 가입 양식에 따라 회원 정보를 기입하여 신청합니다.
  2. 회사는 다음 각 호에 해당하는 신청에 대하여는 승낙을 하지 않거나 사후에 이용계약을 해지할 수 있습니다:
  - 허위 정보를 기재한 경우
  - 타인의 명의를 도용한 경우
  - 기타 회사가 정한 이용신청 요건이 미비한 경우
  제5조 (회원정보의 변경)
  회원은 회원가입 시 기재한 정보가 변경되었을 경우, 즉시 온라인 수정을 하거나 이메일 등의 방법으로 회사에 그 변경 사실을 알려야 합니다.

  제6조 (개인정보 보호)
  회사는 관련 법령이 정하는 바에 따라 회원의 개인정보를 보호하기 위해 노력합니다. 개인정보의 보호 및 사용에 대해서는 관련 법령 및 회사의 개인정보 보호정책이 적용됩니다.

  제7조 (회원의 ID 및 비밀번호 관리)
  1. 회원의 ID와 비밀번호에 관한 관리 책임은 회원에게 있습니다.
  2. 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안 됩니다.
  3. 회원이 자신의 ID 및 비밀번호를 도난당하거나 제3자가 사용하고 있음을 인지한 경우, 즉시 회사에 통보하고 회사의 안내에 따라야 합니다.
  제8조 (서비스 이용)
  1. 서비스의 이용은 회사의 업무상 또는 기술상 특별한 지장이 없는 한 연중무휴, 1일 24시간 가능합니다.
  2. 회사는 서비스의 제공을 일시적으로 중단할 필요가 있는 경우, 사전 공지 후 중단할 수 있습니다.
  제9조 (서비스의 변경 및 중단)
  1. 회사는 상당한 이유가 있는 경우, 서비스의 내용을 변경하거나 중단할 수 있습니다.
  2. 회사는 서비스의 변경 또는 중단에 대하여 사전에 공지하며, 사전에 공지할 수 없는 부득이한 사유가 있는 경우 사후에 통지할 수 있습니다.
  제10조 (회원의 의무)
  1. 회원은 관계 법령, 본 약관의 규정, 이용안내 및 서비스와 관련하여 공지한 주의사항을 준수하여야 하며, 기타 회사의 업무에 방해되는 행위를 하여서는 안 됩니다.
  2. 회원은 서비스 이용과 관련하여 다음 각 호의 행위를 하여서는 안 됩니다:
   - 타인의 정보를 도용하는 행위
   - 서비스 운영을 방해하는 행위
   - 회사 및 타인의 명예를 훼손하거나 모욕하는 행위
  제11조 (회사의 의무)
  1. 회사는 관련 법령과 본 약관이 금지하거나 미풍양속에 반하는 행위를 하지 않으며, 지속적이고 안정적으로 서비스를 제공하기 위하여 노력합니다.
  2. 회사는 회원의 개인정보를 보호하기 위해 보안 시스템을 갖추며, 개인정보 보호정책을 공시하고 준수합니다.
  제12조 (계약 해지 및 서비스 이용 제한)
  1. 회원이 이용계약을 해지하고자 하는 때에는 회원 본인이 온라인을 통해 회사에 해지 신청을 하여야 합니다.
  2. 회사는 회원이 본 약관의 의무를 위반하거나 서비스의 정상적인 운영을 방해한 경우, 사전 통지 후 이용계약을 해지하거나 서비스 이용을 제한할 수 있습니다.
  제13조 (면책 조항)
  1. 회사는 천재지변, 불가항력 또는 이에 준하는 사유로 인해 서비스를 제공할 수 없는 경우에는 서비스 제공에 대한 책임이 면제됩니다.
  2. 회사는 회원의 귀책사유로 인한 서비스 이용의 장애에 대하여 책임을 지지 않습니다.
                    `}
                ></textarea>
              </div>
              <div className="form-group">
                <label htmlFor="privacy">개인정보 수집 및 이용 동의</label>
                <textarea
                    id="privacy"
                    readOnly
                    value={`  제1조 (개인정보의 수집 항목 및 수집 방법)
  1. 회사는 다음의 개인정보를 수집하고 있습니다:
    - 필수 항목: 성명, 생년월일, 성별, 이메일 주소
    - 선택 항목: 건강 진단서 정보, 의료 기록
  2. 개인정보는 회원가입, 서비스 이용, 고객 상담을 위해 수집됩니다.
  제2조 (개인정보의 수집 및 이용 목적)
  회사는 수집한 개인정보를 다음의 목적을 위해 이용합니다:

  1. 회원 관리: 회원 가입 의사 확인, 회원제 서비스 제공, 개인 식별, 불량 회원의 부정 이용 방지
  2. 서비스 제공: 건강 진단서 해석 및 질병 예측 서비스 제공, 맞춤형 건강 정보 제공
  3. 통계 분석: 서비스 이용 통계 분석, 서비스 개선
  제3조 (개인정보의 보유 및 이용 기간)
회사는 회원 탈퇴 시까지 개인정보를 보유 및 이용합니다. 단, 관계 법령에 따라 보존이 필요한 경우에는 해당 기간 동안 보유합니다.

  제4조 (개인정보의 제3자 제공)
회사는 원칙적으로 회원의 개인정보를 제3자에게 제공하지 않습니다. 다만, 다음의 경우에는 예외로 합니다:

  1. 회원이 사전에 동의한 경우
  2. 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
  제5조 (개인정보의 처리 위탁)
  회사는 원활한 서비스 제공을 위해 개인정보 처리를 외부에 위탁할 수 있습니다. 이 경우 위탁 대상자와 위탁 업무 내용을 사전에 고지하고 동의를 받습니다.

  제6조 (회원의 권리)
  회원은 언제든지 등록되어 있는 자신의 개인정보를 조회하거나 수정할 수 있으며, 개인정보 처리에 대한 동의 철회 및 회원 탈퇴를 요청할 수 있습니다.
                    `}
                ></textarea>
              </div>
              <div className="form-group">
                <label>
                  <input
                      type="checkbox"
                      checked={agreeTerms}
                      onChange={handleAgreeTermsChange}
                  />
                  이용약관에 동의합니다
                </label>
              </div>
              <div className="form-group">
                <label>
                  <input
                      type="checkbox"
                      checked={agreePrivacy}
                      onChange={handleAgreePrivacyChange}
                  />
                  개인정보 수집 및 이용에 동의합니다
                </label>
              </div>
              <div className="buttons">
                <button type="submit" className="join-btn">제출</button>
                <button type="button" className="close-btn" onClick={onClose}>닫기</button>
              </div>
            </form>
          </div>
        </div>
      </div>
  );
}

export default Useagree;
