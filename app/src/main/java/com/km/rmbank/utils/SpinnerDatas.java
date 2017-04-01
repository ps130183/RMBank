package com.km.rmbank.utils;


import com.ps.androidlib.widget.nicespinner.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/2/17.
 */

public class SpinnerDatas {

    /**-----------------------对公---------------------------*/
    //信用等级
    public static List<SpinnerModel> sCiLevelList;
    //客户规模
    public static List<SpinnerModel> sClientScaleList;
    //行业
    public static List<SpinnerModel> sIndustryList;
    //贷款产品名称
    public static List<SpinnerModel> sILoanProductList;
    //币种
    public static List<SpinnerModel> sLoanCurrencyList;
    //期限单位
    public static List<SpinnerModel> sTermDetailTypeList;
    //利率类型
    public static List<SpinnerModel> sInterestList;
    //浮动频率
    public static List<SpinnerModel> sFloatingFreuencyList;
    //还款方式
    public static List<SpinnerModel> sRepaymentType;
    //还款频率
    public static List<SpinnerModel> sRepaymentFrequency;
    //抵押品类型
    public static List<SpinnerModel> sGuaranteeTypeList;
    //担保类型
    public static List<SpinnerModel> sAssuranceTypeList;
    //派生业务类型
    public static List<SpinnerModel> sDeriveBusinessList;
    //专项调整类型
    public static List<SpinnerModel> sAdjustTypeList;

    /**-----------------------个人---------------------------*/

    //证件类型
    public static List<SpinnerModel> sCertificateTypeList;
    //个人贷款 产品名称
    public static List<SpinnerModel> sPersonalProductList;

    //存款价格发布
    //条线
    public static List<SpinnerModel> sDepositLinesList;
    //产品
    public static List<SpinnerModel> sDepositProductList;


    static {
        initCiLevelList();
        initClientScaleList();
        initIndustryList();
        initLoanProductList();
        initLoanCurrencyList();
        initTermDetailTypeList();
        initInterestList();
        initFloatingFreuencyList();
        initRepaymentFrequency();
        initRepaymentType();
        initGuaranteeTypeList();
        initsAssuranceTypeList();
        initsDeriveBusinessList();
        initsAdjustTypeList();

        initsCertificateTypeList();
        initsPersonalProductList();

        //存款定价
        initsDepositLinesList();
        initsDepositProductList();
    }

    //信用等级
    private static void initCiLevelList(){
        sCiLevelList = new ArrayList<>();
        sCiLevelList.add(new SpinnerModel("A","A"));
        sCiLevelList.add(new SpinnerModel("AA","AA"));
        sCiLevelList.add(new SpinnerModel("AAA","AAA"));
        sCiLevelList.add(new SpinnerModel("B","B"));
        sCiLevelList.add(new SpinnerModel("BB","BB"));
        sCiLevelList.add(new SpinnerModel("BBB","BBB"));
        sCiLevelList.add(new SpinnerModel("C","C"));
        sCiLevelList.add(new SpinnerModel("CC","CC"));
        sCiLevelList.add(new SpinnerModel("CCC","CCC"));
    }
    //客户规模
    private static void initClientScaleList(){
        sClientScaleList = new ArrayList<>();
        sClientScaleList.add(new SpinnerModel("11","大型企业"));
        sClientScaleList.add(new SpinnerModel("12","中型企业"));
        sClientScaleList.add(new SpinnerModel("13","小型企业"));
        sClientScaleList.add(new SpinnerModel("14","微型企业"));
        sClientScaleList.add(new SpinnerModel("99","其他"));
    }
    //行业
    private static void initIndustryList(){
        sIndustryList = new ArrayList<>();
        sIndustryList.add(new SpinnerModel("A0000","农、林、牧、渔业"));
        sIndustryList.add(new SpinnerModel("B0000","采矿业"));
        sIndustryList.add(new SpinnerModel("C0000","制造业"));
        sIndustryList.add(new SpinnerModel("D0000","电力、热力、燃气及水生产和供应业"));
        sIndustryList.add(new SpinnerModel("E0000","建筑业"));
        sIndustryList.add(new SpinnerModel("F0000","批发和零售业"));
        sIndustryList.add(new SpinnerModel("G0000","交通运输、仓储和邮政业"));
        sIndustryList.add(new SpinnerModel("H0000","住宿和餐饮业"));
        sIndustryList.add(new SpinnerModel("I0000","信息传输、软件和信息技术服务业"));
        sIndustryList.add(new SpinnerModel("J0000","金融业"));
        sIndustryList.add(new SpinnerModel("K0000","房地产业"));
        sIndustryList.add(new SpinnerModel("L0000","租赁和商务服务业"));
        sIndustryList.add(new SpinnerModel("M0000","科学研究和技术服务业"));
        sIndustryList.add(new SpinnerModel("N0000","水利、环境和公共设施管理业"));
        sIndustryList.add(new SpinnerModel("O0000","居民服务、修理和其他服务业"));
        sIndustryList.add(new SpinnerModel("P0000","教育"));
        sIndustryList.add(new SpinnerModel("Q0000","卫生和社会工作"));
        sIndustryList.add(new SpinnerModel("R0000","文化、体育和娱乐业"));
        sIndustryList.add(new SpinnerModel("S0000","公共管理、社会保障和社会组织"));
        sIndustryList.add(new SpinnerModel("T0000","国际组织"));
    }
    //贷款产品名称
    private static void initLoanProductList(){
        sILoanProductList = new ArrayList<>();
        sILoanProductList.add(new SpinnerModel("403","短期农业经济组织信用贷款"));
        sILoanProductList.add(new SpinnerModel("404","短期农村工商业信用贷款"));
        sILoanProductList.add(new SpinnerModel("405","短期农村工商业保证贷款"));
        sILoanProductList.add(new SpinnerModel("411","中长期农业经济组织信用贷款"));
        sILoanProductList.add(new SpinnerModel("412","中长期农业经济组织保证贷款"));
        sILoanProductList.add(new SpinnerModel("413","中长期农村工商业信用贷款"));
        sILoanProductList.add(new SpinnerModel("414","中长期农村工商业保证贷款"));
        sILoanProductList.add(new SpinnerModel("425","短期农业经济组织抵押贷款"));
        sILoanProductList.add(new SpinnerModel("426","中长期农业经济组织抵押贷款"));
        sILoanProductList.add(new SpinnerModel("427","短期农村工商业抵押贷款"));
        sILoanProductList.add(new SpinnerModel("428","中长期农村工商业抵押贷款"));
        sILoanProductList.add(new SpinnerModel("438","短期农业经济组织质押贷款"));
        sILoanProductList.add(new SpinnerModel("439","中长期农业经济组织质押贷款"));
        sILoanProductList.add(new SpinnerModel("440","短期农村工商业质押贷款"));
        sILoanProductList.add(new SpinnerModel("441","中长期农村工商业质押贷款"));
    }
    //币种
    private static void initLoanCurrencyList(){
        sLoanCurrencyList = new ArrayList<>();
        sLoanCurrencyList.add(new SpinnerModel("CNY","人民币"));
    }
    //期限单位
    private static void initTermDetailTypeList(){
        sTermDetailTypeList = new ArrayList<>();
        sTermDetailTypeList.add(new SpinnerModel("D","日"));
        sTermDetailTypeList.add(new SpinnerModel("M","月"));
        sTermDetailTypeList.add(new SpinnerModel("Y","年"));
    }
    //利率类型
    private static void initInterestList(){
        sInterestList = new ArrayList<>();
        sInterestList.add(new SpinnerModel("FDLL","浮动利率"));
        sInterestList.add(new SpinnerModel("GDLL","固定利率"));
    }
    //浮动频率
    private static void initFloatingFreuencyList(){
        sFloatingFreuencyList = new ArrayList<>();
        sFloatingFreuencyList.add(new SpinnerModel("1","1M"));
        sFloatingFreuencyList.add(new SpinnerModel("3","3M"));
        sFloatingFreuencyList.add(new SpinnerModel("6","6M"));
        sFloatingFreuencyList.add(new SpinnerModel("12","12M"));
    }
    //还款方式
    private static void initRepaymentType(){
        sRepaymentType = new ArrayList<>();
        sRepaymentType.add(new SpinnerModel("DEBJ","等额本金"));
        sRepaymentType.add(new SpinnerModel("DEBX","等额本息"));
        sRepaymentType.add(new SpinnerModel("DQHBFCFX","到期还本分次付息"));
        sRepaymentType.add(new SpinnerModel("DQYCHBFX","到期一次还本付息"));
        sRepaymentType.add(new SpinnerModel("HKJH","还款计划"));
    }
    //还款频率
    private static void initRepaymentFrequency(){
        sRepaymentFrequency = new ArrayList<>();
        sRepaymentFrequency.add(new SpinnerModel("1","1M"));
        sRepaymentFrequency.add(new SpinnerModel("3","3M"));
        sRepaymentFrequency.add(new SpinnerModel("6","6M"));
        sRepaymentFrequency.add(new SpinnerModel("12","12M"));
    }
    //抵押品类型
    private static void initGuaranteeTypeList(){
        sGuaranteeTypeList = new ArrayList<>();
        sGuaranteeTypeList.add(new SpinnerModel("WJ10","【抵押】土地使用权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ20","【抵押】房屋所有权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ30","【抵押】动产"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ31","【抵押】动产-汽车"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ32","【抵押】动产-机器设备"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ33","【抵押】动产-船舶"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ34","【抵押】动产-其他"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ35","【抵押】动产-航空器"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ40","【抵押】林权抵押"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ50","【抵押】在建工程"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ60","【抵押】其他-采矿权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ70","【抵押】动产质押"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ80","【抵押】权利质押"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ81","【抵押】存单-个人定期存单"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ82","【抵押】存单-单位定期存单"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ83","【抵押】股权-上市公司流通股"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ84","【抵押】股权-上市公司非流通股"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ85","【抵押】低风险国债"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ86","【抵押】债券-政府债券"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ87","【抵押】债券-企业债券"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ88","【抵押】低风险承兑汇票-银行承兑汇票"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ89","【抵押】承兑汇票-银行承兑汇票"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8A","【抵押】承兑汇票-商业承兑汇票"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8B","【抵押】应收账款-交易类"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8C","【抵押】应收账款-公路收费权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8D","【抵押】应收账款-其他收费权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8E","【抵押】应收账款-应收租金"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8F","【抵押】货权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8G","【抵押】其他"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8H","【抵押】商标权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8I","【抵押】专利权"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8J","【抵押】股权-非上市公司"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ8K","【抵押】债券-金融债券"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ90","【保证】保证"));
        sGuaranteeTypeList.add(new SpinnerModel("WJ99","【担保公司担保】担保"));

    }
    //担保类型
    private static void initsAssuranceTypeList(){
        sAssuranceTypeList = new ArrayList<>();
        sAssuranceTypeList.add(new SpinnerModel("010","一般担保"));
    }
    //派生业务类型
    private static void initsDeriveBusinessList(){
        sDeriveBusinessList = new ArrayList<>();
        sDeriveBusinessList.add(new SpinnerModel("200204","二年定期存款"));
        sDeriveBusinessList.add(new SpinnerModel("201403","银行承兑汇票保证金"));
        sDeriveBusinessList.add(new SpinnerModel("200203","一年定期存款"));
        sDeriveBusinessList.add(new SpinnerModel("201407","担保贷款保证金"));
        sDeriveBusinessList.add(new SpinnerModel("20020702","单位七天通知"));
        sDeriveBusinessList.add(new SpinnerModel("200201","三个月定期存款"));
        sDeriveBusinessList.add(new SpinnerModel("200101","单位活期存款"));
        sDeriveBusinessList.add(new SpinnerModel("200202","六个月定期存款"));
        sDeriveBusinessList.add(new SpinnerModel("201402","保函保证金"));
        sDeriveBusinessList.add(new SpinnerModel("201499","其他保证金"));
        sDeriveBusinessList.add(new SpinnerModel("201404","银行卡保证金"));
    }
    //专项调整类型
    private static void initsAdjustTypeList(){
        sAdjustTypeList = new ArrayList<>();
        sAdjustTypeList.add(new SpinnerModel("3035197","办理我行多项中间业务"));
        sAdjustTypeList.add(new SpinnerModel("3035198","在我行开立外币结算账户"));
        sAdjustTypeList.add(new SpinnerModel("3035199","开办国际业务"));
        sAdjustTypeList.add(new SpinnerModel("3035200","与我行有长期合作关系"));
        sAdjustTypeList.add(new SpinnerModel("3179222","在我行开立基本结算账户"));
    }

    //证件类型
    private static void initsCertificateTypeList(){
        sCertificateTypeList = new ArrayList<>();
        sCertificateTypeList.add(new SpinnerModel("IDENTIFYID","身份证"));
        sCertificateTypeList.add(new SpinnerModel("PASSPORT","护照"));
        sCertificateTypeList.add(new SpinnerModel("ENSUREID","社会保障号"));
        sCertificateTypeList.add(new SpinnerModel("OTHERS","其他"));
    }

    //贷款产品名称
    private static void initsPersonalProductList(){
        sPersonalProductList = new ArrayList<>();
        sPersonalProductList.add(new SpinnerModel("401","短期农户信用贷款"));
        sPersonalProductList.add(new SpinnerModel("402","短期农户保证贷款"));
        sPersonalProductList.add(new SpinnerModel("403","短期农业经济组织信用贷款"));
        sPersonalProductList.add(new SpinnerModel("404","短期农业经济组织保证贷款"));
        sPersonalProductList.add(new SpinnerModel("405","短期农村工商业信用贷款"));
        sPersonalProductList.add(new SpinnerModel("406","短期农村工商业保证贷款"));
        sPersonalProductList.add(new SpinnerModel("407","短期其他信用贷款"));
        sPersonalProductList.add(new SpinnerModel("408","短期其他保证贷款"));
        sPersonalProductList.add(new SpinnerModel("409","中长期农户信用贷款"));
        sPersonalProductList.add(new SpinnerModel("410","中长期农户保证贷款"));
        sPersonalProductList.add(new SpinnerModel("411","中长期农业经济组织信用贷款"));
        sPersonalProductList.add(new SpinnerModel("412","中长期农业经济组织保证贷款"));
        sPersonalProductList.add(new SpinnerModel("413","中长期农村工商业信用贷款"));
        sPersonalProductList.add(new SpinnerModel("414","中长期农村工商业保证贷款"));
        sPersonalProductList.add(new SpinnerModel("415","中长期其他信用贷款"));
        sPersonalProductList.add(new SpinnerModel("416","中长期其他保证贷款"));
        sPersonalProductList.add(new SpinnerModel("417","农户小额信用贷款（短期）"));
        sPersonalProductList.add(new SpinnerModel("418","农户联保贷款（短期）"));
        sPersonalProductList.add(new SpinnerModel("421","生源地助学贷款"));
        sPersonalProductList.add(new SpinnerModel("422","其他助学贷款"));
        sPersonalProductList.add(new SpinnerModel("423","短期农户抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("424","中长期农户抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("425","短期农业经济组织抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("426","中长期农业经济组织抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("427","短期农村工商业抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("428","中长期农村工商业抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("429","短期其他抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("430","中长期其他抵押贷款"));
        sPersonalProductList.add(new SpinnerModel("431","个人住房按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("432","个人汽车按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("433","个人家居装修贷款"));
        sPersonalProductList.add(new SpinnerModel("434","个人大额耐用消费品贷款"));
        sPersonalProductList.add(new SpinnerModel("435","其他消费贷款"));
        sPersonalProductList.add(new SpinnerModel("436","短期农户质押贷款"));
        sPersonalProductList.add(new SpinnerModel("437","中长期农户质押贷款"));
        sPersonalProductList.add(new SpinnerModel("438","短期农业经济组织质押贷款"));
        sPersonalProductList.add(new SpinnerModel("439","中长期农业经济组织质押贷款"));
        sPersonalProductList.add(new SpinnerModel("440","短期农村工商业质押贷款"));
        sPersonalProductList.add(new SpinnerModel("441","中长期农村工商业质押贷款"));
        sPersonalProductList.add(new SpinnerModel("442","短期其他质押贷款"));
        sPersonalProductList.add(new SpinnerModel("443","中长期其他质押贷款"));
        sPersonalProductList.add(new SpinnerModel("455","商用房按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("456","营运汽车按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("457","商用房装修按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("458","经营设备按揭贷款"));
        sPersonalProductList.add(new SpinnerModel("459","其他经营贷款"));
    }

    //存款价格发布  --- 条线
    private static void initsDepositLinesList(){
        sDepositLinesList = new ArrayList<>();
        sDepositLinesList.add(new SpinnerModel("","---请选择---"));
        sDepositLinesList.add(new SpinnerModel("CREDIT","对公条线"));
        sDepositLinesList.add(new SpinnerModel("RETAIL","零售条线"));
        sDepositLinesList.add(new SpinnerModel("DESPOST","存款条线"));
    }

    //存款价格发布  --- 产品
    private static void initsDepositProductList(){
        sDepositProductList = new ArrayList<>();
        sDepositProductList.add(new SpinnerModel("","---请选择---"));
        sDepositProductList.add(new SpinnerModel("101","单位活期存款"));
        sDepositProductList.add(new SpinnerModel("111","单位定期三个月"));
        sDepositProductList.add(new SpinnerModel("121","单位定期六个月"));
        sDepositProductList.add(new SpinnerModel("131","单位定期一年"));
        sDepositProductList.add(new SpinnerModel("141","单位定期二年"));
        sDepositProductList.add(new SpinnerModel("151","单位定期三年"));
        sDepositProductList.add(new SpinnerModel("161","单位定期五年"));
        sDepositProductList.add(new SpinnerModel("171","协定存款"));
        sDepositProductList.add(new SpinnerModel("181","单位通知存款(1天)"));
        sDepositProductList.add(new SpinnerModel("191","单位通知存款(7天)"));
        sDepositProductList.add(new SpinnerModel("201","个人活期存款"));
        sDepositProductList.add(new SpinnerModel("211","个人整存整取三个月"));
        sDepositProductList.add(new SpinnerModel("221","个人整存整取六个月"));
        sDepositProductList.add(new SpinnerModel("231","个人整存整取一年"));
        sDepositProductList.add(new SpinnerModel("241","个人整存整取二年"));
        sDepositProductList.add(new SpinnerModel("251","个人整存整取三年"));
        sDepositProductList.add(new SpinnerModel("261","个人整存整取五年"));
        sDepositProductList.add(new SpinnerModel("331","个人零存整取、整存零取、存本取息一年"));
        sDepositProductList.add(new SpinnerModel("351","个人零存整取、整存零取、存本取息三年"));
        sDepositProductList.add(new SpinnerModel("361","个人零存整取、整存零取、存本取息五年"));
        sDepositProductList.add(new SpinnerModel("501","定活两便储蓄存款"));
        sDepositProductList.add(new SpinnerModel("511","个人通知存款(1天)"));
        sDepositProductList.add(new SpinnerModel("521","个人通知存款(7天)"));
    }


    /**
     * 根据名称获取对应的位置
     * @param name
     * @param mode
     * @return
     */
    public static int getPositionByName(String name,SpinnerMode mode){
        int position = 0;
        List<SpinnerModel> spinnerModels = getSpinnerModels(mode);
        for (int i = 0;i < spinnerModels.size();i++){
            if (name.equals(spinnerModels.get(i).getName())){
                position = i;
                break;
            } else {
                position = 0;
            }
        }
        return position;
    }

    public static SpinnerModel getSpinnerModel(int selectIndex, SpinnerMode mode){
        List<SpinnerModel> spinnerModels = getSpinnerModels(mode);
        return spinnerModels.get(selectIndex);
    }

    public static SpinnerModel getSpinnerModelById(String id, SpinnerMode mode){
        SpinnerModel spinnerModel = null;
        List<SpinnerModel> spinnerModels = getSpinnerModels(mode);
        for (int i = 0;i < spinnerModels.size();i++){
            if (id.equals(spinnerModels.get(i).getId())){
                spinnerModel = spinnerModels.get(i);
                break;
            }
        }
        return spinnerModel;
    }

    public enum SpinnerMode{
        CI_LEVEL,
        CLIENT_SCALE,
        INDUSTRY,
        LOAN_PRODUCT,
        LOAN_CURRENCY,
        TERMDETAILTYPE,
        INTEREST,
        FLOATING_FREQUENCY,
        REPAYMENT_TYPE,
        REPAYMENT_FREQUENCY,
        GUARANTEE_TYPE_LIST,
        ASSURANCE_TYPE_LIST,
        DERIVE_BUSINESS_LIST,
        ADJUST_TYPE_LIST,
        CERTIFICATE_TYPE,
        PERSONAL_PRODUCT_LIST,
        DEPOSIT_LINES,
        DEPOSIT_PRODUCT;
    }

    private static List<SpinnerModel> getSpinnerModels(SpinnerMode spinnerMode){
        List<SpinnerModel> spinnerModels = null;
        switch (spinnerMode){
            case CI_LEVEL:
                spinnerModels = sCiLevelList;
                break;
            case CLIENT_SCALE:
                spinnerModels = sClientScaleList;
                break;
            case INDUSTRY:
                spinnerModels = sIndustryList;
                break;
            case LOAN_PRODUCT:
                spinnerModels = sILoanProductList;
                break;
            case LOAN_CURRENCY:
                spinnerModels = sLoanCurrencyList;
                break;
            case TERMDETAILTYPE:
                spinnerModels = sTermDetailTypeList;
                break;
            case INTEREST:
                spinnerModels = sInterestList;
                break;
            case FLOATING_FREQUENCY:
                spinnerModels = sFloatingFreuencyList;
                break;
            case REPAYMENT_TYPE:
                spinnerModels = sRepaymentType;
                break;
            case REPAYMENT_FREQUENCY:
                spinnerModels = sRepaymentFrequency;
                break;
            case GUARANTEE_TYPE_LIST:
                spinnerModels = sGuaranteeTypeList;
                break;
            case ASSURANCE_TYPE_LIST:
                spinnerModels = sAssuranceTypeList;
                break;
            case DERIVE_BUSINESS_LIST:
                spinnerModels = sDeriveBusinessList;
                break;
            case ADJUST_TYPE_LIST:
                spinnerModels = sAdjustTypeList;
                break;
            case CERTIFICATE_TYPE:
                spinnerModels = sCertificateTypeList;
                break;
            case PERSONAL_PRODUCT_LIST:
                spinnerModels = sPersonalProductList;
                break;
            case DEPOSIT_LINES:
                spinnerModels = sDepositLinesList;
                break;
            case DEPOSIT_PRODUCT:
                spinnerModels = sDepositProductList;
                break;

            default:
                spinnerModels = new ArrayList<>();
                break;
        }
        return spinnerModels;
    }

    /**
     * 根据性别获取相应id
     * @param sex
     * @return
     */
    public static String getSexId(String sex){
        if ("男".equals(sex)){
            return "5337";
        } else {
            return "5338";
        }
    }

    /**
     * 根据ID获取性别
     * @param id
     * @return
     */
    public static String getSexById(String id){
        if ("5337".equals(id)){
            return "男";
        } else {
            return "女";
        }
    }
}
