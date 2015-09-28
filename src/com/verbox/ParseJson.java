/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verbox;

import static com.verbox.DecodeUTF.DecodeUTF;
import static com.verbox.ErrorList.DesctiptError;
import static com.verbox.StorageMemory.getInstance;
import static com.verbox.sqlite_metod.DELETE_ALL;
import static com.verbox.sqlite_metod.Insert;
import static com.verbox.sqlite_metod.UPDATE;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author maxxl
 */
public class ParseJson {

    private String ErrorCode;
    private String ErrorMessage;

    //курсы валют
    ArrayList Currencies;
    String TimetoFinish,TimetoStart;
    String date_create,time_create,cartulary_id;
    // для левой логинизации касиры
    ArrayList CashierNameList;
    
   

    public ArrayList getCashierNameList() {
        if (!CashierNameList.isEmpty()) {
            return CashierNameList;
        }
        return null;
    }

    //инициализация обекта наверное для ероров
    JSONObject PJjson;

    //конструктор
    public ParseJson(JSONObject json) throws ClassNotFoundException, SQLException {
        //Ошибки
        if(json.isEmpty())
        {
        System.out.println(json.toJSONString());
        }
        else
        {
        this.PJjson = json;
        
        
        // Ловим ошибки
        if(PJjson.get("error")!=null)
        {
        JSONObject Error = (JSONObject) PJjson.get("error");
        setErrorCode((String) Error.get("code"));
        setErrorMessage((String) Error.get("message"));
       if (!"not_cashier".equals(getErrorMessage()))
       {showMessageDialog(null, DesctiptError(getErrorMessage()));}
        }      
        else{
                                    if(json.get("params")!=null)
                                    {
                                        try{
                                    JSONArray msg = (JSONArray) json.get("params");
                                        if(msg.size()==1)
                                        {
                                            showMessageDialog(null,msg.toJSONString()+"Выполнено ");
                                            if(msg.toJSONString().equals("[openday]"))
                                            {
                                                        boolean ido=UPDATE("UPDATE SDobj SET id_operation= 1 ;");
                                                        boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= 1 ;");
                                                        showMessageDialog(null, "[] need to openday");
                                            }
                                            if(msg.toJSONString().equals("openday"))
                                            {
                                                        boolean ido=UPDATE("UPDATE SDobj SET id_operation= 1 ;");
                                                        boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= 1 ;");
                                                        showMessageDialog(null, "only openday");
                                            }
                                            if(msg.toJSONString().equals("open_day"))
                                            {
                                                        boolean ido=UPDATE("UPDATE SDobj SET id_operation= 1 ;");
                                                        boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= 1 ;");
                                                        showMessageDialog(null, "open_day");
                                            }
                                            if(msg.toJSONString().equals("[open_day]"))
                                            {
                                                        boolean ido=UPDATE("UPDATE SDobj SET id_operation= 1 ;");
                                                        boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= 1 ;");
                                                        showMessageDialog(null, "[open_day]");
                                            }
                                        }
                                        msg.clear();
                                        }
                                        catch(ClassCastException Ex)
                                        {
                                            System.out.println("Ловим екзепшин при удачсных операциях");
                                        }
                                    }     
                                    
                                    
                                    
                       //Забираем курсы с сервака   
                       if(json.get("action_name").equals("get_currencies"))
                       {
                           showMessageDialog(null,json.get("action_name"));
                                    if(json.get("params")!=null)
                                   {
                                    JSONObject tmp = (JSONObject) json.get("params");
                                    TimetoFinish =  (String) tmp.get("date_expiration");
                                    TimetoStart =  (String) tmp.get("date_create");
                                    JSONArray msg = (JSONArray) tmp.get("currencies"); 
                                    Currencies = new ArrayList();
                                    for (int i = 0; i < msg.size(); i++) 
                                    {
                                    JSONObject mas2 = (JSONObject) msg.get(i);
                                    Currencies.add(mas2.get("course_buy"));
                                    Currencies.add(mas2.get("course_nbu"));
                                    Currencies.add(mas2.get("course_sale"));
                                    Currencies.add(mas2.get("currency_code"));
                                    Currencies.add(mas2.get("currency_id"));
                                    Currencies.add(mas2.get("currency_name"));
                                    Currencies.add(mas2.get("order_id"));
                                    Currencies.add(mas2.get("quantity"));
                                    Currencies.add(mas2.get("quantity_text"));
                                     
                                    }
                                   } 
                        }
                       //Принимаем платежи в приходящем JSON
                       //-----------------------------------
                       //Покупка buy
                       if(json.get("action_name").equals("buy"))
                       {
                                   if(json.get("params")!=null)
                                   {
                                    JSONObject tmp = (JSONObject) json.get("params");
                                    
                                            if(tmp.get("type").equals("buy"))
                                            {
                                                showMessageDialog(null, "Схавал покупку");
                                                StorageMemory sd =getInstance();
                                                         date_create =  (String) tmp.get("date_create");
                                                         time_create =  (String) tmp.get("time_create");
                                                         cartulary_id =  tmp.get("cartulary_id").toString();

                                                         ArrayList key = new ArrayList();
                                                         key.add("buyer_first_name");
                                                         key.add("buyer_last_name");
                                                         key.add("buyer_surname");
                                                         key.add("buyer_resident");
                                                         key.add("currency_code");
                                                         key.add("currency_course");
                                                         key.add("currency_sum");
                                                         key.add("grn_sum");
                                                         key.add("passport_number");
                                                         key.add("passport_serial");
                                                         key.add("phone_number");
                                                         key.add("receipt_currency");
                                                         key.add("taxpf");
                                                         key.add("date_create");
                                                         key.add("time_create");
                                                         key.add("type");
                                                         key.add("cartulary_id");


                                                         ArrayList value = new ArrayList();
                                                         value.add(sd.getBuyer_first_name());
                                                         value.add(sd.getBuyer_last_name());
                                                         value.add(sd.getBuyer_surname());
                                                         value.add(sd.getBuyer_resident());
                                                         value.add(sd.getCurrency_code());
                                                         value.add(sd.getCurrency_course());
                                                         value.add(sd.getCurrency_sum());
                                                         value.add(sd.getGrn_sum());
                                                         value.add(sd.getPassport_number());
                                                         value.add(sd.getPassport_serial());
                                                         value.add(sd.getPhone_number());
                                                         value.add(sd.getReceipt_currency());
                                                         value.add(sd.getTaxpf());
                                                         value.add(date_create);
                                                         value.add(time_create);
                                                         value.add(tmp.get("type"));
                                                         value.add(cartulary_id);
                                                         

                                                         boolean ido=UPDATE("UPDATE SDobj SET id_operation= \""+(sd.getId_operation()+1)+"\" ;" );
                                                         boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= \""+(sd.getIdqwi()+1)+"\" ;" );
                                                         
                                                         boolean ins =Insert("journal",key,value);
                                                        showMessageDialog(null,"ID operation " + ido + "ID qwi updated "+ idqwi+"Insert "+ins);
                                                         sd.initCourse() ; 
                                            }       
                                   } 
                        }
                       
                       //Продажа sale
                       if(json.get("action_name").equals("sale"))
                       {
                                   if(json.get("params")!=null)
                                   {
                                    JSONObject tmp = (JSONObject) json.get("params");
                                    
                                            if(tmp.get("type").equals("sale"))
                                            {
                                                showMessageDialog(null, "Схавал продажу");
                                                StorageMemory sd =getInstance();
                                                         date_create =  (String) tmp.get("date_create");
                                                         time_create =  (String) tmp.get("time_create");
                                                         cartulary_id =  tmp.get("cartulary_id").toString();

                                                         ArrayList key = new ArrayList();
                                                         key.add("buyer_first_name");
                                                         key.add("buyer_last_name");
                                                         key.add("buyer_surname");
                                                         key.add("buyer_resident");
                                                         key.add("currency_code");
                                                         key.add("currency_course");
                                                         key.add("currency_sum");
                                                         key.add("grn_sum");
                                                         key.add("passport_number");
                                                         key.add("passport_serial");
                                                         key.add("phone_number");
                                                         key.add("receipt_currency");
                                                         key.add("taxpf");
                                                         key.add("date_create");
                                                         key.add("time_create");
                                                         key.add("type");
                                                         key.add("cartulary_id");


                                                         ArrayList value = new ArrayList();
                                                         value.add(sd.getBuyer_first_name());
                                                         value.add(sd.getBuyer_last_name());
                                                         value.add(sd.getBuyer_surname());
                                                         value.add(sd.getBuyer_resident());
                                                         value.add(sd.getCurrency_code());
                                                         value.add(sd.getCurrency_course());
                                                         value.add(sd.getCurrency_sum());
                                                         value.add(sd.getGrn_sum());
                                                         value.add(sd.getPassport_number());
                                                         value.add(sd.getPassport_serial());
                                                         value.add(sd.getPhone_number());
                                                         value.add(sd.getReceipt_currency());
                                                         value.add(sd.getTaxpf());
                                                         value.add(date_create);
                                                         value.add(time_create);
                                                         value.add(tmp.get("type"));
                                                         value.add(cartulary_id);
                                                         

                                                         boolean ido=UPDATE("UPDATE SDobj SET id_operation= \""+(sd.getId_operation()+1)+"\" ;" );
                                                         boolean idqwi=UPDATE("UPDATE SDobj SET idqwi= \""+(sd.getIdqwi()+1)+"\" ;" );
                                                         
                                                         boolean ins =Insert("journal",key,value);
                                                        showMessageDialog(null,"ID operation " + ido + "ID qwi updated "+ idqwi+"Insert "+ins);
                                                         sd.initCourse() ; 
                                            }       
                                   } 
                        }
                       
                       //Пополнение - "replenish"
                       
                       
                       
                       //Инкасация  collection
                                  
                                    
                                    
            }
        
        
        
        
        
        
        
        
        
        
//"super":false,"cashier_id":4,"cash_id":1,"cashier_password":"c4ca4238a0b923820dcc509a6f75849b","surname":"Петров","last_name":"Александрович","first_name":"Петя"
                                    //Касиры список      
                               if(json.get("cashiers")!=null)
                                    {
                                    CashierNameList = new ArrayList();
                                    if (getErrorCode().equals("08")) {
                                    } else {
                                    JSONArray msg = (JSONArray) json.get("cashiers");
                                      for (int i = 0; i < msg.size(); i++) {
                                    JSONObject mas2 = (JSONObject) msg.get(i);
                                    CashierNameList.add(mas2.get("cashier_id"));
                                    CashierNameList.add(mas2.get("cashier_password"));
                                    CashierNameList.add(mas2.get("cash_id"));
                                    CashierNameList.add(mas2.get("super"));
                                    CashierNameList.add(mas2.get("surname"));
                                    CashierNameList.add(mas2.get("last_name"));
                                    CashierNameList.add(mas2.get("first_name"));
                                    }
                                    }
                                    }
                                   
                                    
                                   
                                    
                 
                 
                 
        }
    }
    
    //---- записывает инфу в бд
    public static void  get_info(JSONObject json) throws SQLException, UnsupportedEncodingException, ClassNotFoundException
    {
         //getinfo 
    ArrayList info,infonamespace;
        info = new ArrayList();
        infonamespace = new ArrayList();
        if(json.isEmpty())
        {
        System.out.println(json.toJSONString());
        }
        else
        {
   //"super":false,"cashier_id":4,"cash_id":1,"cashier_password":"c4ca4238a0b923820dcc509a6f75849b","surname":"Петров","last_name":"Александрович","first_name":"Петя"
            JSONObject jo = (JSONObject) json.get("params");
            JSONObject enterprise = (JSONObject) jo.get("enterprise");
                info.add(enterprise.get("bookk_fio"));
                info.add(enterprise.get("bookk_first_name"));
                info.add(enterprise.get("bookk_last_name"));
                info.add(enterprise.get("bookk_surname"));
                info.add(enterprise.get("dir_fio"));
                info.add(enterprise.get("dir_first_name"));
                info.add(enterprise.get("dir_last_name"));
                info.add(enterprise.get("dir_surname"));
                info.add(enterprise.get("enterprise_full_name"));
                info.add(enterprise.get("enterprise_mfo"));
                info.add(enterprise.get("enterprise_name"));
                info.add(enterprise.get("enterprise_okpo"));
                info.add(enterprise.get("enterprise_short_name"));
                info.add(enterprise.get("logo_full_image"));
                info.add(enterprise.get("logo_short_image"));
                info.add(enterprise.get("nat_city"));
                info.add(enterprise.get("nat_city_code"));
                info.add(enterprise.get("nat_house"));
                info.add(enterprise.get("nat_office"));
                info.add(enterprise.get("nat_street"));
                info.add(enterprise.get("payment_account"));
                info.add(enterprise.get("stamp_image"));
                info.add(enterprise.get("ur_city"));
                info.add(enterprise.get("ur_city_code"));
                info.add(enterprise.get("ur_house"));
                info.add(enterprise.get("ur_office"));
                info.add(enterprise.get("ur_street"));
                //--    
                infonamespace.add("bookk_fio");
                infonamespace.add("bookk_first_name");
                infonamespace.add("bookk_last_name");
                infonamespace.add("bookk_surname");
                infonamespace.add("dir_fio");
                infonamespace.add("dir_first_name");
                infonamespace.add("dir_last_name");
                infonamespace.add("dir_surname");
                infonamespace.add("enterprise_full_name");
                infonamespace.add("enterprise_mfo");
                infonamespace.add("enterprise_name");
                infonamespace.add("enterprise_okpo");
                infonamespace.add("enterprise_short_name");
                infonamespace.add("logo_full_image");
                infonamespace.add("logo_short_image");
                infonamespace.add("nat_city");
                infonamespace.add("nat_city_code");
                infonamespace.add("nat_house");
                infonamespace.add("nat_office");
                infonamespace.add("nat_street");
                infonamespace.add("payment_account");
                infonamespace.add("stamp_image");
                infonamespace.add("ur_city");
                infonamespace.add("ur_city_code");
                infonamespace.add("ur_house");
                infonamespace.add("ur_office");
                infonamespace.add("ur_street");
                
                
                
            boolean flag = Insert("info", infonamespace, info);
            //--  
        }
        /*  for (Object item : CashierNameList) 
                    {
                        System.out.println("Name of user  "+ item);
                    }*/
        
    }
//-----
    
    
    private void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    private void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
    
    //запись курсов валют в бд 
    public boolean Write_CurrenciesToSQLite() throws SQLException, ClassNotFoundException {
        boolean flag = false;
        if (!Currencies.isEmpty()) {
                   
            int i = 0;
            while (i < Currencies.size()) {

                ArrayList key = new ArrayList();
                key.add("course_buy");
                key.add("course_nbu");
                key.add("course_sale");
                key.add("currency_code");
                key.add("currency_id");
                key.add("currency_name");
                key.add("order_id");
                key.add("quantity");
                key.add("quantity_text");
                key.add("TimetoFinish");                   
                key.add("TimetoStart"); 
                
                ArrayList value = new ArrayList();
                value.add(Currencies.get(i).toString());
                value.add(Currencies.get(i+1).toString());
                value.add(Currencies.get(i+2).toString());
                value.add(Currencies.get(i+3).toString());
                value.add(Currencies.get(i+4).toString());
                value.add(Currencies.get(i+5).toString());
                value.add(Currencies.get(i+6).toString());
                value.add(Currencies.get(i+7).toString());
                value.add(Currencies.get(i+8).toString());
                value.add(TimetoFinish);
                value.add(TimetoStart);
                flag = Insert("currencies", key, value);

                i += 9;
            }
 /*           
for (Object item : Currencies) {
	
        System.out.println("Currencies "+item.toString());
}
*/
StorageMemory sd =getInstance();
           sd.initCourse() ; 
           
           
           
            return flag;
        }
        return flag;

    }
    //запись касиров касы и паролей в бд
        public boolean Write_LoginToSQLite() throws SQLException, ClassNotFoundException {
        boolean flag = false;
        if (!CashierNameList.isEmpty()) {

            int i = 4;
            while (i < CashierNameList.size()) {
                ArrayList key = new ArrayList();
                key.add("cashier_id");
                key.add("cashier_password");
                key.add("cash_id");
                key.add("super");
                key.add("surname");
                key.add("last_name");
                key.add("first_name");
                key.add("visible");
                ArrayList value = new ArrayList();
                value.add(CashierNameList.get(i - 4).toString());
                value.add(CashierNameList.get(i - 3).toString());
                value.add(CashierNameList.get(i - 2).toString());
                value.add(CashierNameList.get(i - 1).toString());
                value.add(CashierNameList.get(i).toString());
                value.add(CashierNameList.get(i + 1).toString());
                value.add(CashierNameList.get(i + 2).toString());
                value.add("true");
                flag = Insert("user", key, value);
                i += 7;
            }

            return flag;
        }
        return flag;

    }
    
    
    
    
    
    
    
    
    
    
}
