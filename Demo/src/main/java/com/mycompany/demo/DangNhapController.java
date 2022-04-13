/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo;

import com.mycompany.conf.Utils;
import com.mycompany.pojo.Employee;
import com.mycompany.services.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author HIEN
 */
public class DangNhapController {
    @FXML private TextField username;
    @FXML private TextField password;
    private static final EmployeeService es = new EmployeeService();
    private static Employee employee = new Employee();
    
    public void DangNhap(ActionEvent e){
        try{
            employee = es.getEmployeeByUser(username.getText(), password.getText());
            if (employee.getUserRoleId()==1 || employee.getUserRoleId()==2){
                Utils.getBox("Nhân viên", Alert.AlertType.CONFIRMATION).show();
            }
            else if(employee.getUserRoleId()==3 || employee.getUserRoleId()==4){
                Utils.getBox("Quản lí và Cửa hàng trưởng", Alert.AlertType.CONFIRMATION).show();
            }
            else {
                Utils.getBox("Không tìm thấy chức vụ !", Alert.AlertType.ERROR).show();
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
