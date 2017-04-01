package com.km.rmbank.api;


import com.google.gson.Gson;
import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.RetrofitUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by kamangkeji on 17/2/11.
 */

public class ApiWrapper extends RetrofitUtil {

    private static Gson gson = null;
    public static final int maxData = 10;//每页返回的数据条数
    /**
     * 单例
     */
    private static ApiWrapper instance = null;
    private ApiWrapper(){}
    public static ApiWrapper getInstance(){
        if (instance == null){
            synchronized (ApiWrapper.class){
                if (instance == null){
                    instance = new ApiWrapper();
                    gson = new Gson();
                }
            }
        }
        return  instance;
    }

    /**
     * 登录
     * @param mobilePhone
     * @param loginPwd
     * @return
     */
    public Observable<UserDto> login(String mobilePhone, String loginPwd){
        return getService().login(mobilePhone,loginPwd).compose(this.<UserDto>applySchedulers());
    }

    /**
     * 注册
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Observable<DefaultDto> userRegister(String mobilePhone,String loginPwd,String smsCode){
        return getService().userRegister(mobilePhone,loginPwd,smsCode).compose(this.<DefaultDto>applySchedulers());
    }


    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    public Observable<DefaultDto> getPhoneCode(String mobilePhone){
        return getService().getPhoneCode(mobilePhone).compose(this.<DefaultDto>applySchedulers());
    }

    /**
     * 忘记密码
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Observable<DefaultDto> forgetLoginPwd(String mobilePhone,String loginPwd,String smsCode){
        return getService().forgetLoginPwd(mobilePhone,loginPwd,smsCode).compose(this.<DefaultDto>applySchedulers());
    }

    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.user.getToken()).compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 修改个人信息
     * @param userInfoDto
     * @return
     */
    public Observable<String> updateUserInfo(UserInfoDto userInfoDto){
        return getService().updateUserInfo(Constant.user.getToken(),userInfoDto.getNickName(),
                userInfoDto.getPortraitUrl(),userInfoDto.getBirthday()).compose(this.<String>applySchedulers());
    }

    /**
     * 生成个人名片
     * @param userCardDto
     * @return
     */
    public Observable<UserCardDto> createUserCart(UserCardDto userCardDto){
        return getService().createUserCard(Constant.user.getToken(),userCardDto.getName(),userCardDto.getCardPhone(),
                userCardDto.getCompany(),userCardDto.getPosition(),userCardDto.getCompanyProfile(),
                userCardDto.getProvideResourcesId(),userCardDto.getDemandResourcesId(),
                userCardDto.getLocation(),userCardDto.getDetailedAddress(),userCardDto.getEmailAddress())
                .compose(this.<UserCardDto>applySchedulers());
    }

    /**
     * 获取个人名片
     * @return
     */
    public Observable<UserCardDto> getUserCard(){
        return getService().getUserCard(Constant.user.getToken()).compose(this.<UserCardDto>applySchedulers());
    }
    /**
     * 获取行业
     * @return
     */
    public Observable<List<IndustryDto>> getIndustryList(){
        return getService().getIndustryList(Constant.user.getToken()).compose(this.<List<IndustryDto>>applySchedulers());
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Observable<String> imageUpload(String optionType,String imagePath){

        File image = new File(imagePath);

        RetrofitUtil util = new RetrofitUtil();
        RequestBody imageRequset = util.createPictureRequestBody(imagePath);
        RequestBody requestOptionType = util.createRequestBody(optionType);
        Map<String,RequestBody> params = new HashMap<>();
        params.put("optionType",requestOptionType);
        params.put("pictureFile\"; filename=\"" + image.getName() + "", imageRequset);

        return getService().imageUpload(params).compose(this.<String>applySchedulers());
    }

    /**
     * 获取账户余额
     * @return
     */
    public Observable<UserBalanceDto> getUserAccountBalance(){
        return getService().getUserAccountBalance(Constant.user.getToken()).compose(this.<UserBalanceDto>applySchedulers());
    }

    /**
     * 获取账户明细
     * @param pageNo
     * @return
     */
    public Observable<List<UserAccountDetailDto>> getUserAccountDetail(int pageNo){
        return getService().getUserAccountDetail(Constant.user.getToken(),pageNo)
                .compose(this.<List<UserAccountDetailDto>>applySchedulers());
    }

    /**
     * 新增提现账户
     * @param withDrawAccountDto
     * @return
     */
    public Observable<String> createWithDrawAccount(WithDrawAccountDto withDrawAccountDto){
        return getService().createWithDrawAccount(Constant.user.getToken(),
                withDrawAccountDto.getName(),withDrawAccountDto.getWithdrawPhone(),
                withDrawAccountDto.getTypeName(),withDrawAccountDto.getWithdrawNumber())
                .compose(this.<String>applySchedulers());
    }
    /**
     * 编辑提现账户
     * @param withDrawAccountDto
     * @return
     */
    public Observable<String> updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto){
        return getService().updateWithDrawAccount(Constant.user.getToken(),
                withDrawAccountDto.getName(),withDrawAccountDto.getWithdrawPhone(),
                withDrawAccountDto.getTypeName(),withDrawAccountDto.getWithdrawNumber())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取提现账户列表
     * @return
     */
    public Observable<List<WithDrawAccountDto>> getWithDrawAccount(){
        return getService().getWithDrawAccount(Constant.user.getToken())
                .compose(this.<List<WithDrawAccountDto>>applySchedulers());
    }

    /**
     * 获取商城的 商品列表
     * @param pageNo
     * @return
     */
    public Observable<List<GoodsDto>> getGoodsListOfShopping(int pageNo){
        return getService().getGoodsListOfShopping(pageNo)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }
}
