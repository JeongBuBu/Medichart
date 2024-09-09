import React from "react";
import AdminHeader from "./AdminHeader";
import AdminFooter from "./AdminFooter";
import styles from "./layout.module.css";

const AdminLayout = (props: { children: React.ReactNode }) => {
  return (
    <div className={styles.layout}>
      <AdminHeader />

      <main>{props.children}</main>

      <AdminFooter />
    </div>
  );
};

export default AdminLayout;
