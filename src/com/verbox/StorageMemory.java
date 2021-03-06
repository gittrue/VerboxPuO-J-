/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verbox;

import static com.verbox.MyMath.round;
import static com.verbox.Serial_XDD.Serial_XDDGet;
import static com.verbox.Serial_XDD.Serial_XDDGetHash;
import static com.verbox.sqlite_metod.GetMd5;
import static com.verbox.sqlite_metod.ReadSQLite;
import static com.verbox.sqlite_metod.ReadSQLiteMulti;
import static com.verbox.sqlite_metod.UPDATE;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.RowFilter.Entry;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.json.simple.JSONObject;
import static com.verbox.Setting.GetZeroArr;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author maxxl
 */
public class StorageMemory {

    //generate cashier_id and cashier_password for sign in
    private static JSONObject obj = new JSONObject();
    public boolean internet,superuser;
    private String cash_id, cash_password, cashier_id, cashier_password, action_name, lastmess,patterns,FIO;
    JSONObject params;
    private ArrayList get_info;
//поля покупателя валюты для отправки запроса
    private String 
            buyer_first_name,
            buyer_last_name,
            buyer_surname,
		buyer_resident,
            passport_number,
            passport_serial,
            phone_number,
            currency_code,
            currency_course,
            currency_sum,
            grn_sum,
            receipt_currency,
            taxpf,
            receipt_number,
		cartulary_id,
		type;
//поля покупателя валюты
    
    //хранение курсов в обьекте
    private int 
            id_operation, //receipt_currency
            idqwi,  // квитанция
            idqwiadmin; //для инкасаторов
public int Pfsell,

    /**
     *
     */
    Pfbuy;     //пенсионный фонд на продажу

      
    //хранение курсов в обьекте
    //массив валюты тест
    public  Map curse,

    /**
     *
     */
    balance;

    /**
     *
     */
    public  ArrayList TempForSelectDropdown;
    
    //Массив шаблонов на печать 

    /**
     *
     */
    public Map PrintTpl;
    
    public VelocityContext velocity;
    public Map TPLveloPrint,
		 ListLinkedForPr;
    //для индексов
  public double pfvalue;//для количества комисии
    StorageMemory() {
    }

    /**
     *
     * @param TempForSelectDropdown
     */
    public void setTempForSelectDropdown(ArrayList TempForSelectDropdown) {
        this.TempForSelectDropdown = TempForSelectDropdown;
    }

    private static volatile StorageMemory instance;

    /**
     *
     * @return
     */
    public static  StorageMemory getInstance() {
        if (instance == null) {
            synchronized (StorageMemory.class) {
                if (instance == null) {
                    instance = new StorageMemory();
                }
                System.out.println("Create new Instanse Singlton");
            }
        }
        System.out.println("Return Instanse Singlton");

        return instance;
    }

    
    
    // добавляет данные в обьект OBJ из класса и ретурн

    /**
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public JSONObject GetSD() throws UnsupportedEncodingException {
        obj.clear();
        obj.put("cash_id", getCash_id());
        obj.put("cash_password", getCash_password());
        obj.put("cashier_id", getCashier_id());
        obj.put("cashier_password", getCashier_password());
        obj.put("action_name", getAction_name());
        obj.put("params", getParams());
        obj.put("lastmes", getLastmess());
        return obj;
    }

    /**
     *
     */
    public void ClearObj()
    {
        obj.clear();
    }
    //эти аргументы мы инициализируем из функции логинизации sqlite metod login они хранятся в памяти постоянно

    /**
     *
     * @param t1
     * @param t3
     * @param t4
     * @param t5
     * @param t6
     * @param t7
     * @param t8
     * @throws UnsupportedEncodingException
     */
    public void StorageMemorySet(String t1, String t3, String t4, String t5, JSONObject t6, String t7, String t8) throws UnsupportedEncodingException {
        Serial_XDDGet();
        String tmp = Serial_XDDGetHash();

        setCash_id(t1);
        setCash_password(tmp);
        setCashier_id(t3);
        setCashier_password(t4);
        setAction_name(t5);
        setParams(t6);
        setLastmess(t7);
        setFIO(t8);


    }

    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void SetInfo() throws SQLException, ClassNotFoundException {
        get_info = new ArrayList();
        ArrayList tmp = new ArrayList();
        tmp.add("bookk_fio");               //ФИО бухгалтера
        tmp.add("bookk_first_name");        //Имя бухгалтера
        tmp.add("bookk_last_name");         //отчество
        tmp.add("bookk_surname");           //фамилия бухг
        tmp.add("dir_fio");                 //ФИО директора
        tmp.add("dir_first_name");          //Имя директора
        tmp.add("dir_last_name");           //Отчество Директора 
        tmp.add("dir_surname");             //Фамилия директора
        tmp.add("enterprise_full_name");    //полное имя предприятия
        tmp.add("enterprise_mfo");          //МФО 
        tmp.add("enterprise_name");         // я без понятия что это
        tmp.add("enterprise_okpo");         //ОКПО предприятия
        tmp.add("enterprise_short_name");   //короткое имя предприятия
        tmp.add("logo_full_image");         //имя лого
        tmp.add("logo_short_image");        //Имя еще одной картинки тоже хз
        tmp.add("nat_city");                //Физ адр города
        tmp.add("nat_city_code");           //физ индекс города
        tmp.add("nat_house");               //физ номер дома
        tmp.add("nat_office");              //физ офис
        tmp.add("nat_street");              //физ улица
        tmp.add("payment_account");         //расчетный счет
        tmp.add("stamp_image");             // наверное печать предприятия
        tmp.add("ur_city");                 //юр адрес город
        tmp.add("ur_city_code");            //юр код города
        tmp.add("ur_house");                //юр дом
        tmp.add("ur_office");               //юр дом
        tmp.add("ur_street");               // юр улица

        get_info = ReadSQLite(tmp, "info","ORDER BY `enterprise_id` DESC LIMIT 1");
        //добавление в шаблонизатор инфы о предприятии
        //инициализация
        velocity = new VelocityContext();
        //добавление в шаблонизатор общей информации
        try{
        velocity.put("bookk_fio", get_info.get(0));
        velocity.put("bookk_first_name", get_info.get(1));
        velocity.put("bookk_last_name", get_info.get(2));
        velocity.put("bookk_surname", get_info.get(3));
        velocity.put("dir_fio", get_info.get(4));
        velocity.put("dir_first_name", get_info.get(5));
        velocity.put("dir_last_name", get_info.get(6));
        velocity.put("dir_surname", get_info.get(7));
        velocity.put("enterprise_full_name", get_info.get(8));
        velocity.put("enterprise_mfo", get_info.get(9));
        velocity.put("enterprise_name", get_info.get(10));
        velocity.put("enterprise_okpo", get_info.get(11));
        velocity.put("enterprise_short_name", get_info.get(12));
        velocity.put("logo_full_image", get_info.get(13));
        velocity.put("logo_short_image", get_info.get(14));
        velocity.put("nat_city", get_info.get(15));
        velocity.put("nat_city_code", get_info.get(16));
        velocity.put("nat_house", get_info.get(17));
        velocity.put("nat_office", get_info.get(18));
        velocity.put("nat_street", get_info.get(19));
        velocity.put("payment_account", get_info.get(20));
        velocity.put("stamp_image", get_info.get(21));
        velocity.put("ur_city", get_info.get(22));
        velocity.put("ur_city_code", get_info.get(23));
        velocity.put("ur_house", get_info.get(24));
        velocity.put("ur_office", get_info.get(25));
        velocity.put("ur_street", get_info.get(26));
        }
        catch(Exception E)
        {
            showMessageDialog(null, "Не удалось добавить в шаблонизатор данные о предприятии");
        }
        
        
    }
    //---Методы получения гет инфо структуры

    //ФИО бухг

    /**
     *
     * @param who
     * @return
     */
    public String StorageGetInfo(String who) {

        int index = 0;
              
    switch (who) {
                
                case "bookk_fio": index=0; break;               //ФИО бухгалтера
                case "bookk_first_name": index=1; break;        //Имя бухгалтера
                case "bookk_last_name": index=2; break;         //отчество
                case "bookk_surname": index=3; break;           //фамилия бухг
                case "dir_fio": index=4; break;                 //ФИО директора
                case "dir_first_name": index=5; break;          //Имя директора
                case "dir_last_name": index=6; break;           //Отчество Директора 
                case "dir_surname": index=7; break;             //Фамилия директора
                case "enterprise_full_name": index=8; break;    //полное имя предприятия
                case "enterprise_mfo": index=9; break;          //МФО 
                case "enterprise_name": index=10; break;         // я без понятия что это
                case "enterprise_okpo": index=11; break;         //ОКПО предприятия
                case "enterprise_short_name": index=12; break;   //короткое имя предприятия
                case "logo_full_image": index=13; break;         //имя лого
                case "logo_short_image": index=14; break;        //Имя еще одной картинки тоже хз
                case "nat_city": index=15; break;                //Физ адр города
                case "nat_city_code": index=16; break;           //физ индекс города
                case "nat_house": index=17; break;               //физ номер дома
                case "nat_office": index=18; break;              //физ офис
                case "nat_street": index=19; break;              //физ улица
                case "payment_account": index=20; break;         //расчетный счет
                case "stamp_image": index=21; break;             // наверное печать предприятия
                case "ur_city": index=22; break;                 //юр адрес город
                case "ur_city_code": index=23; break;            //юр код города
                case "ur_house": index=24; break;                //юр дом
                case "ur_office": index=25; break;               //юр дом
                case "ur_street": index=26; break;               // юр улица  
    }
    
        if (get_info.get(index) == null) {
            return "NULL SD getinfo memory";
        } else {
            return (String) get_info.get(index);
        }
    }

    //---Методы получения гет инфо структуры конец

    /**
     *
     * @return
     */
    public String getCash_id() {
        return cash_id;
    }

    /**
     *
     * @param cash_id
     */
    public void setCash_id(String cash_id) {
        this.cash_id = cash_id;
    }

    /**
     *
     * @return
     */
    public String getCash_password() {
        return cash_password;
    }

    /**
     *
     * @param cash_password
     */
    public void setCash_password(String cash_password) {
        this.cash_password = cash_password;
    }

    /**
     *
     * @return
     */
    public String getCashier_id() {
        return cashier_id;
    }

    /**
     *
     * @param cashier_id
     */
    public void setCashier_id(String cashier_id) {
        this.cashier_id = cashier_id;
    }

    /**
     *
     * @return
     */
    public String getCashier_password() {
        return cashier_password;
    }

    /**
     *
     * @param cashier_password
     */
    public void setCashier_password(String cashier_password) {
        this.cashier_password = cashier_password;
    }

    /**
     *
     * @return
     */
    public String getAction_name() {
        return action_name;
    }

    /**
     *
     * @param action_name
     */
    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    /**
     *
     * @return
     */
    public JSONObject getParams() {
        return params;
    }

    /**
     *
     * @param params
     */
    public void setParams(JSONObject params) {
        this.params = params;
    }

    /**
     *
     * @return
     */
    public String getLastmess() {
        return lastmess;
    }

    /**
     *
     * @param lastmes
     */
    public void setLastmess(String lastmes) {
        this.lastmess = lastmes;
    }



//методы покупателя

    /**
     *
     * @return
     */
    
    public String getBuyer_first_name() {
        return buyer_first_name;
    }

    /**
     *
     * @param buyer_first_name
     */
    public void setBuyer_first_name(String buyer_first_name) {
        this.buyer_first_name = buyer_first_name;
    }

    /**
     *
     * @return
     */
    public String getBuyer_last_name() {
        return buyer_last_name;
    }

    /**
     *
     * @param buyer_last_name
     */
    public void setBuyer_last_name(String buyer_last_name) {
        this.buyer_last_name = buyer_last_name;
    }

    /**
     *
     * @return
     */
    public String getBuyer_surname() {
        return buyer_surname;
    }

    /**
     *
     * @param buyer_surname
     */
    public void setBuyer_surname(String buyer_surname) {
        this.buyer_surname = buyer_surname;
    }

    /**
     *
     * @return
     */
    public String getBuyer_resident() {
        return buyer_resident;
    }

    /**
     *
     * @param buyer_resident
     */
    public void setBuyer_resident(String buyer_resident) {
        this.buyer_resident = buyer_resident;
    }

    /**
     *
     * @return
     */
    public String getPassport_number() {
        return passport_number;
    }

    /**
     *
     * @param passport_number
     */
    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    /**
     *
     * @return
     */
    public String getPassport_serial() {
        return passport_serial;
    }

    /**
     *
     * @param passport_serial
     */
    public void setPassport_serial(String passport_serial) {
        this.passport_serial = passport_serial;
    }

    /**
     *
     * @return
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     *
     * @param phone_number
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     *
     * @return
     */
    public String getCurrency_code() {
        return currency_code;
    }

    /**
     *
     * @param currency_code
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     *
     * @return
     */
    public String getCurrency_course() {
        return currency_course;
    }

    /**
     *
     * @param currency_course
     */
    public void setCurrency_course(String currency_course) {
        this.currency_course = currency_course;
    }

    /**
     *
     * @return
     */
    public String getCurrency_sum() {
        return currency_sum;
    }

    /**
     *
     * @param currency_sum
     */
    public void setCurrency_sum(String currency_sum) {
        this.currency_sum = currency_sum;
    }

    /**
     *
     * @return
     */
    public String getGrn_sum() {
        return grn_sum;
    }

    /**
     *
     * @param grn_sum
     */
    public void setGrn_sum(String grn_sum) {
        this.grn_sum = grn_sum;
    }

    /**
     *
     * @return
     */
    public String getReceipt_currency() {
        return receipt_currency;
    }

    /**
     *
     * @param receipt_currency
     */
    public void setReceipt_currency(String receipt_currency) {
        this.receipt_currency = receipt_currency;
    }

    /**
     *
     * @return
     */
    public String getTaxpf() {
        return taxpf;
    }

    /**
     *
     * @param taxpf
     */
    public void setTaxpf(String taxpf) {
        this.taxpf = taxpf;
    }

    /**
     *
     * @return
     */
    public String getReceipt_number() {
        return receipt_number;
    }

    /**
     *
     * @param receipt_number
     */
    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    /**
     *
     * @return
     */
    public int getId_operation() {
        return id_operation;
    }

    /**
     *
     * @param id_operation
     */
    public void setId_operation(int id_operation) {
        this.id_operation = id_operation;
    }

    /**
     *
     * @return
     */
    public int getIdqwi() {
        return idqwi;
    }

    /**
     *
     * @param idqwi
     */
    public void setIdqwi(int idqwi) {
        this.idqwi = idqwi;
    }

    /**
     *
     * @return
     */
    public int getIdqwiadmin() {
        return idqwiadmin;
    }

    /**
     *
     * @param idqwiadmin
     */
    public void setIdqwiadmin(int idqwiadmin) {
        this.idqwiadmin = idqwiadmin;
    }

    /**
     *
     * @return
     */
    public int getPfsell() {
        return Pfsell;
    }

    /**
     *
     * @param Pfsell
     */
    public void setPfsell(int Pfsell) {
        this.Pfsell = Pfsell;
    }

    /**
     *
     * @return
     */
    public String getPatterns() {
        return patterns;
    }

    /**
     *
     * @param patterns
     */
    public void setPatterns(String patterns) {
        this.patterns = patterns;
    }

    /**
     *
     * @return
     */
    public String getFIO() {
        return FIO;
    }

    /**
     *
     * @param FIO
     */
    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    //===========================================================================================================
    //===========================================================================================================
    //===========================================================================================================
    //функция подсчета принимает код валюты / резидент ли /  фио покупателя / сумму денег / название операции/ паспортные данные
//! должна вернуть готовый обьект JSON!

    /**
     *
     * @param code
     * @param resident
     * @param Fn
     * @param Ln
     * @param Sn
     * @param sum
     * @param csum
     * @param typeOx
     * @param pspS
     * @param pspCode
     * @param Phone
     */
    
    //SELECT id_journal,cartulary_id,type,currency_code,currency_sum,currency_course,grn_sum FROM JOURNAL ORDER by id_journal DESC LIMIT 1;
    public void OperationX(
            int code,        //код валюты
            String resident,    //резидент не резидент
            String Fn,          //Имя
            String Ln,          //Отчество
            String Sn,          //Фамилия
            double sum,          //бабло в грн
            double csum,            // в валюте
            String typeOx,       //Тип операции
           
            String pspS,         //Серия пасспорта
            int pspCode,         //код паспорта
            String Phone
    )
            {
            try{
            buyer_first_name=Fn;
            buyer_last_name=Ln;
            buyer_surname=Sn;
            buyer_resident=resident;
            }
            catch(Exception E)
            {
                System.out.println("OperationX Хреновое имя/фамилия/отчество/статус резидента ");
            }
            try
            {
            phone_number=Phone;
            }
            catch(NumberFormatException e)
            {
            Phone="0";
            }
            //ПФ
                        if (typeOx.equals("sale"))
                        {
                                taxpf=Integer.toString(Pfsell);
                                passport_number=Integer.toString(pspCode);
                                passport_serial=pspS;
                        }
                       
                        if (typeOx.equals("buy"))
                        {
                            taxpf=Integer.toString(Pfbuy);
                            passport_number=Integer.toString(pspCode);
                            passport_serial=pspS;
                            
                        }
                   
            //Код валюты передает кнопка купить /../ и получаем из дропдауна индекс выбраного и подтягиваем из массива.
		if(!typeOx.equals("reversal")&&!typeOx.equals("delete"))
		{
            currency_code=(String) TempForSelectDropdown.get(code);
		currency_sum=Double.toString(csum);
	      grn_sum=Double.toString(sum);
            receipt_currency=Integer.toString(idqwi);
		}
            //получить курс по данной валюте
                                            if(typeOx.equals("buy"))
                                            {  //купить 
                                                    ArrayList tmpz = new ArrayList();
                                                    tmpz=(ArrayList) curse.get(currency_code);
                                                    currency_course=Double.toString(Double.parseDouble((String)tmpz.get(0))*Double.parseDouble((String)tmpz.get(3)));
                                                    action_name="buy";
                                                    
                                               //     grn_sum=Double.toString(sum*Double.parseDouble((String)tmpz.get(0)));
                                            }
                                            else if(typeOx.equals("sale"))
                                            {
                                                // продать  
                                                    ArrayList tmpz = new ArrayList();
                                                    tmpz=(ArrayList) curse.get(currency_code);
                                                    currency_course=Double.toString(Double.parseDouble((String)tmpz.get(1))*Double.parseDouble((String)tmpz.get(3)));
                                                    action_name="sale";
                                              // grn_sum=Double.toString(round(sum*Double.parseDouble((String)tmpz.get(1)),2));
                                            }
							  else if(typeOx.equals("reversal")||typeOx.equals("delete"))
							  {
							  action_name=typeOx;
							  cartulary_id=resident;
							  type=Fn;
							  currency_sum=Double.toString(sum);	     
							  currency_course=pspS;
							  grn_sum=Phone;
							  currency_code=Integer.toString(code);
							  receipt_currency=Ln;
							  }
                                          
       
            
            
            
            JSONObject forparam = new JSONObject();
            //если купить/продать
            if(typeOx.equals("sale")||typeOx.equals("buy")){
            
            
            forparam.put("buyer_first_name", buyer_first_name);
            forparam.put("buyer_last_name", buyer_last_name);
            forparam.put("buyer_surname", buyer_surname);
            forparam.put("buyer_resident", buyer_resident);
            forparam.put("patterns", getPatterns());
            forparam.put("passport_number", passport_number);
            forparam.put("passport_serial", passport_serial);
                    if(!typeOx.equals("buy"))
                    {
                    
                    }
                    if(!phone_number.equals("0"))
                    {
				 forparam.put("phone_number", phone_number);
                    }
		forparam.put("currency_code", currency_code);
		forparam.put("currency_course", currency_course);
		forparam.put("currency_sum", currency_sum);
		forparam.put("grn_sum", grn_sum);
		forparam.put("receipt_currency", receipt_currency);
		forparam.put("buyer_first_name", buyer_first_name);
		forparam.put("taxpf", taxpf);
		setParams(forparam);
            }
            //есди подкрепление или инкасация
            if(typeOx.equals("replenish")||typeOx.equals("collection")){
              action_name=typeOx;
               forparam.put("currency_code", currency_code);
               forparam.put("currency_sum", currency_sum);
               forparam.put("patterns", patterns);
               
                setReceipt_number(Integer.toString(idqwiadmin));
                forparam.put("receipt_number", getReceipt_number());
                setParams(forparam);
      
            }
		
		//удаление или сторно
		if(typeOx.equals("reversal")||typeOx.equals("delete"))
		{
	
		forparam.put("cartulary_id", cartulary_id);
		forparam.put("currency_code", currency_code);
		forparam.put("currency_course", currency_course);
		forparam.put("currency_sum", currency_sum);
		forparam.put("grn_sum", grn_sum);
		forparam.put("receipt_currency", receipt_currency);
		
		forparam.put("type", type);
		 setParams(forparam);
		}
		
		
		
                
                    
                    
                    
                    
           }
    //===========================================================================================================
    //===========================================================================================================
    //===========================================================================================================
    //===========================================================================================================
    //инициализация основных компонентов курсов для подсчета

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void initCourse() throws ClassNotFoundException, SQLException
    {
    
        //получаем курсы с локал бд в ключе номер валюты в массиве посчитанный курс за единицу валюты
        curse=new HashMap<String,ArrayList<String>>();
        curse=ReadSQLiteMulti("SELECT currency_code,course_buy/CAST(quantity AS DOUBLE),course_sale/CAST(quantity AS DOUBLE),course_nbu/CAST(quantity AS DOUBLE),quantity,currency_name FROM `currencies` ORDER BY `currencies_id` DESC LIMIT 22");
        
        //получаем баланс в ключе ид валюты в массиве баланс
        balance=new HashMap<String,Double>();
        balance=ReadSQLiteMulti("SELECT currency_code,balance FROM `SDbalance` ORDER BY `id_SDbalance` DESC LIMIT 23");
        
        
        
        
        

        if(curse!=null||!curse.isEmpty())
        {   
     Set<Entry<String, ArrayList<String>>> setMap = curse.entrySet(); 
     Iterator<Entry<String,  ArrayList<String>>> iteratorMap = setMap.iterator(); 
     System.out.println("\nHashMap with Multiple Values"); 
        
        while(iteratorMap.hasNext()) { 
            Map.Entry<String, ArrayList<String>> entry =  
            (Map.Entry<String, ArrayList<String>>) iteratorMap.next();
            String key = entry.getKey(); 
            List<String> values = entry.getValue(); 
            System.out.println("Key = '" + key + "' has values: " + values); 
      } 


        }
                ArrayList tmpz = new ArrayList();
                tmpz=(ArrayList) curse.get("978");
                System.out.println("GET OBJ "+Double.parseDouble((String)tmpz.get(1))+5); 
        
        
        //map balance
        Set<String> keys = balance.keySet();

	// Loop over String keys.
	for (String key : keys) {
	    System.out.println("key of balance "+key+ " balance "+ balance.get(key));
	}


       
        
        ArrayList tmp=new ArrayList();
        ArrayList res=new ArrayList();
        tmp.add("course_buy");
        tmp.add("course_nbu");
        tmp.add("course_sale");   
        tmp.add("quantity"); 
                // переделать на взятие последних елементов

        
        res.clear();
        tmp.clear();
        tmp.add("id_operation");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.id_operation=Integer.parseInt((String)res.get(0));
         
        res.clear();
        tmp.clear();
        tmp.add("idqwi");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.idqwi=Integer.parseInt((String) res.get(0));
        
        res.clear();
        tmp.clear();
        tmp.add("idqwiadmin");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.idqwiadmin=Integer.parseInt((String) res.get(0));
        
        res.clear();
        tmp.clear();
        tmp.add("Pfsell");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.Pfsell=Integer.parseInt((String) res.get(0));
        
        res.clear();
        tmp.clear();
        tmp.add("Pfbuy");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.Pfbuy=Integer.parseInt((String) res.get(0));
        
        
        res.clear();
        tmp.clear();
        tmp.add("lastmess");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.lastmess= (String) res.get(0);
        
        res.clear();
        tmp.clear();
        tmp.add("patterns");
        res= ReadSQLite(tmp, "SDobj",""); 
        this.patterns= (String) res.get(0);
        
	  
	  //очистка людей хвостов
	setBuyer_surname("");
	setBuyer_first_name("");
	setBuyer_last_name("");
	setPassport_serial("");
	setPassport_number("");
	setPhone_number("");
	setCurrency_code("");
	setCurrency_course("");
	setCurrency_sum("");
	setGrn_sum("");
	
	  
	  
        

       /* showMessageDialog(null, 
                " Lastmess "+ lastmess+
                "\n Patterns "+ patterns+
                "\n Id operation "+ id_operation+
                "\n Idqwi " + idqwi +
                "\n idqwiadmin "+idqwiadmin+
                "\n Pfsell " + Pfsell+
                "\n Pfbuy "+Pfbuy );*/
    }
    
    public static String ShablonThisHtml(String str)
            {
               
            StorageMemory SD = getInstance();
            StringWriter w = new StringWriter();
        
            Velocity.evaluate(SD.velocity, w, "", "cycle"
                + str);
                
            return w.toString();
            }
    public Double getBalance(String codes)
    {
        return Double.parseDouble(GetZeroArr((ArrayList) balance.get(codes)));
    }
    
    public boolean setBalance(String codes,double value) throws SQLException
    {
        double d=Double.parseDouble(GetZeroArr((ArrayList) balance.get(codes)));
        double end =d+value;
      return  UPDATE("UPDATE SDbalance SET balance=\""+end+"\" WHERE currency_code=\""+codes+"\";");
    }
}
