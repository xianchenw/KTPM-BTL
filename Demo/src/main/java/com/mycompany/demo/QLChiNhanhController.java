/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo;

import com.mycompany.conf.Utils;
import com.mycompany.conf.jdbcUtils;
import com.mycompany.pojo.Address;
import com.mycompany.pojo.District;
import com.mycompany.pojo.ExportDataTbEmployee;
import com.mycompany.pojo.Province;
import com.mycompany.pojo.Store;
import com.mycompany.pojo.Ward;
import com.mycompany.services.AllComboboxService;
import com.mycompany.services.EmployeeService;
import com.mycompany.services.StoreService;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Administrator
 */
public class QLChiNhanhController extends TrangChudemo2Controller {
    private static final StoreService s = new StoreService();
    private static final EmployeeService empl = new EmployeeService();
    @FXML
    private TableView<Store> tbStore;
    @FXML
    private TextField txtKeyword ;
    @FXML
    private Button btThem;  
    @FXML private Button btSua;
    @FXML private TextField txtDuong;
    @FXML private ComboBox<Ward> cbWard;
    @FXML private ComboBox<District> cbDistrict;
    @FXML private ComboBox<Province> cbProvince;
    @FXML private TextField txtId;
    @FXML private TextField txtTenCuaHang;
    @FXML private TextField txtDienThoai;
    
    Address ad = new Address();
    Ward w = new Ward();
    District dt = new District();
    Province pv = new Province();
    Store str = new Store(); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AllComboboxService t = new AllComboboxService();
        this.loadColumns();
        this.loadData(null);
        this.txtKeyword.textProperty().addListener((evt) -> {
            this.loadData(this.txtKeyword.getText());
        });
        try {
            
            this.cbProvince.setItems(FXCollections.observableArrayList(s.getTP()));
        } catch (SQLException ex) {
            Logger.getLogger(QLChiNhanhController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tbStore.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                Store q = this.tbStore.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(q.getId()));
                this.txtTenCuaHang.setText(q.getName());
                this.txtDienThoai.setText(q.getPhoneNumber());
                
                
                str = s.getStoreById(q.getId());
                ad = t.getAddressById(str.getAddressId());
                w = t.getWardById(ad.getWardId());
                dt = t.getDistrictById(w.getDistrictId());
                pv = t.getProvinceById(dt.getProvinceId());
                this.txtDuong.setText(ad.getMoreInfo());
                this.cbWard.getSelectionModel().select(w);
                this.cbDistrict.getSelectionModel().select(dt);
                this.cbProvince.getSelectionModel().select(pv);
              
                
                this.btSua.setVisible(true);
            });
            return row;
        });
        
    }
    //ph??n lo???i cbbox theo th??nh ph???, qu???n, ph?????ng HONG UPDATE 24/4
   public void SelectProvince (ActionEvent evt) throws SQLException {
       AllComboboxService t = new AllComboboxService();
       int provinceid = this.cbProvince.getSelectionModel().getSelectedItem().getId();
       this.cbDistrict.setItems(FXCollections.observableList(t.getDistrictByProvinceId(provinceid)));
   }
   public void SelectDistrict (ActionEvent evt) throws SQLException {
       AllComboboxService t = new AllComboboxService();
       int Districtid = this.cbDistrict.getSelectionModel().getSelectedItem().getId();
       this.cbWard.setItems(FXCollections.observableList(t.getWardByDistrictId(Districtid)));
   }
    private void loadData(String kw) {
        try {
            this.tbStore.setItems(FXCollections.observableList(s.gettbStore(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(QLChiNhanhController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   private void loadColumns() {
        
        TableColumn col1 = new TableColumn("M?? chi nh??nh");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col1.setPrefWidth(90);
        
        TableColumn col2 = new TableColumn("T??n chi nh??nh");
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setPrefWidth(230);
        
        TableColumn col3 = new TableColumn("?????a ch???");
        col3.setCellValueFactory(new PropertyValueFactory<>("full_address"));
        col3.setPrefWidth(300);
        
        TableColumn col4 = new TableColumn("Hotline");
        col4.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col4.setPrefWidth(100);
        TableColumn col5 = new TableColumn("Thao t??c");
        col5.setCellFactory((p) -> {
            Button btn = new Button("X??a");
            
            btn.setOnAction((evt) -> {
                Alert confirm =  new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("B???n c?? ch???c ch???n mu???n x??a chi nh??nh n??y?");
                confirm.showAndWait().ifPresent(res->{
                    if(res== ButtonType.OK){
                        TableCell c = (TableCell)((Button)evt.getSource()).getParent();
                        Store q = (Store) c.getTableRow().getItem();
                      
                        try {
                            s.deleteStore(q.getId());
                            this.tbStore.getItems().clear();
                            this.loadData(null);
                            this.reset();
                           
                        } catch (SQLException ex) {
                            Logger.getLogger(QLChiNhanhController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        this.tbStore.getColumns().addAll( col1, col2, col3, col4, col5);
     
    }
   public void addStore(ActionEvent evt) throws SQLException {
        Random idrandom = new Random();
        Address ar = new Address(30+idrandom.nextInt(1000), this.txtDuong.getText(), this.cbWard.getSelectionModel().getSelectedItem().getId());
        s.addAddress(ar);
        
        Store st = new Store(this.txtTenCuaHang.getText(), this.txtDienThoai.getText(), ar.getId());
            try{
            s.addStore(st);
            this.reset();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Th??m chi nh??nh th??nh c??ng!");
            alert.show();
            this.loadData(null);
            }catch(SQLException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Th??m chi nh??nh kh??ng th??nh c??ng: " + ex.getMessage());
                alert.show();
            }
   }
   //UPDATE 22/4
   public void updateStore (ActionEvent event ) throws SQLException{
       Store q = this.tbStore.getSelectionModel().getSelectedItem();
       if(q!=null){
            try{
                Connection conn = jdbcUtils.getConn();
                //L???y d??? li???u c???p nh???t ?????a ch??? chi nh??nh
                     
                int a1 = s.getStoreById(q.getId()).getAddressId();
                String a2=  this.txtDuong.getText();
                int a3 = this.cbWard.getSelectionModel().getSelectedItem().getId();
                String sql2 = "UPDATE address Set more_info = '"+a2+"', ward_id ='"+a3+"' WHERE id = '"+a1+"'";
                PreparedStatement stm2 = conn.prepareStatement(sql2);
                stm2.execute();
                //Lay d??? li???u c???p nh???t chi nh??nh
                int v1 = q.getId();
                String v2 = this.txtTenCuaHang.getText();
                String v3 = this.txtDienThoai.getText();
                int v4 = s.getStoreById(q.getId()).getAddressId();
                String sql1 = "UPDATE store Set name ='"+v2+"', phone_number = '"+v3+"', address_id= '"+v4+"' WHERE id ='"+v1+"'";
                PreparedStatement stm1 = conn.prepareStatement(sql1);
                
                stm1.execute();    
                this.tbStore.getItems().clear();
                this.reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("S???a th??ng tin chi nh??nh th??nh c??ng!");
                alert.show();
                this.loadData(null);
            }catch(SQLException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("S???a th??ng tin chi nh??nh kh??ng th??nh c??ng: " + ex.getMessage());
                alert.show();
            }
       }
   }
   public void reset(){
        this.txtId.setText("");
        this.txtTenCuaHang.setText("");
        this.txtDienThoai.setText("");
        this.txtDuong.setText("");  
        this.cbProvince.getSelectionModel().select(null);
        this.cbDistrict.getSelectionModel().select(null);
        this.cbWard.getSelectionModel().select(null);
          
    }  
   
}
