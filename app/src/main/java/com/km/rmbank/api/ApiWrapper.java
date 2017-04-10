package com.km.rmbank.api;


import com.google.gson.Gson;
import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.km.rmbank.utils.fileupload.UploadFileRequestBody;
import com.km.rmbank.utils.retrofit.RetrofitUtil;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
//import rx.Observable;
//import rx.functions.Action1;
//import rx.functions.Func1;

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
    public Flowable<UserDto> login(String mobilePhone, String loginPwd){
        return getService().login(mobilePhone,loginPwd).compose(this.<UserDto>applySchedulers());
    }

    /**
     * 注册
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Flowable<DefaultDto> userRegister(String mobilePhone,String loginPwd,String smsCode){
        return getService().userRegister(mobilePhone,loginPwd,smsCode).compose(this.<DefaultDto>applySchedulers());
    }


    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    public Flowable<DefaultDto> getPhoneCode(String mobilePhone){
        return getService().getPhoneCode(mobilePhone).compose(this.<DefaultDto>applySchedulers());
    }

    /**
     * 忘记密码
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Flowable<DefaultDto> forgetLoginPwd(String mobilePhone,String loginPwd,String smsCode){
        return getService().forgetLoginPwd(mobilePhone,loginPwd,smsCode).compose(this.<DefaultDto>applySchedulers());
    }

    /**
     * 获取用户信息
     * @return
     */
    public Flowable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.user.getToken()).compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 修改个人信息
     * @param userInfoDto
     * @return
     */
    public Flowable<String> updateUserInfo(UserInfoDto userInfoDto){
        return getService().updateUserInfo(Constant.user.getToken(),userInfoDto.getNickName(),
                userInfoDto.getPortraitUrl(),userInfoDto.getBirthday()).compose(this.<String>applySchedulers());
    }

    /**
     * 生成个人名片
     * @param userCardDto
     * @return
     */
    public Flowable<UserCardDto> createUserCart(UserCardDto userCardDto){
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
    public Flowable<UserCardDto> getUserCard(){
        return getService().getUserCard(Constant.user.getToken()).compose(this.<UserCardDto>applySchedulers());
    }
    /**
     * 获取行业
     * @return
     */
    public Flowable<List<IndustryDto>> getIndustryList(){
        return getService().getIndustryList(Constant.user.getToken()).compose(this.<List<IndustryDto>>applySchedulers());
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Flowable<String> imageUpload(String optionType, String imagePath){
//        RetrofitUtil util = new RetrofitUtil();
//        RequestBody requestOptionType = util.createRequestBody(optionType);
//        final Map<String,RequestBody> params = new HashMap<>();
//        params.put("optionType",requestOptionType);
//
//        final File image = new File(imagePath);
//        RequestBody imageRequset = util.createPictureRequestBody(imagePath);
//        params.put("pictureFile\"; filename=\"" + image.getName() + "", imageRequset);

        return imageUpload(optionType,imagePath,null);
    }

    /**
     * 上传图片
     * @param optionType
     * @param imagePath
     * @return
     */
    public Flowable<String> imageUpload(String optionType, String imagePath, FileUploadingListener fileUploadObserver){
        RetrofitUtil util = new RetrofitUtil();
        RequestBody requestOptionType = util.createRequestBody(optionType);

        File image = new File(imagePath);
        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(image, fileUploadObserver);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pictureFile", image.getName(), uploadFileRequestBody);

        return getService().imageUpload(requestOptionType,part).compose(this.<String>applySchedulers());
    }

    /**
     * 获取账户余额
     * @return
     */
    public Flowable<UserBalanceDto> getUserAccountBalance(){
        return getService().getUserAccountBalance(Constant.user.getToken()).compose(this.<UserBalanceDto>applySchedulers());
    }

    /**
     * 获取账户明细
     * @param pageNo
     * @return
     */
    public Flowable<List<UserAccountDetailDto>> getUserAccountDetail(int pageNo){
        return getService().getUserAccountDetail(Constant.user.getToken(),pageNo)
                .compose(this.<List<UserAccountDetailDto>>applySchedulers());
    }

    /**
     * 新增提现账户
     * @param withDrawAccountDto
     * @return
     */
    public Flowable<String> createWithDrawAccount(WithDrawAccountDto withDrawAccountDto){
        return getService().createWithDrawAccount(Constant.user.getToken(),
                withDrawAccountDto.getName(),withDrawAccountDto.getWithdrawPhone(),
                withDrawAccountDto.getTypeName(),withDrawAccountDto.getWithdrawNumber())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除提现账户
     * @param id
     * @return
     */
    public Flowable<String> deleteWithdrawAccount(String id){
        return getService().deleteWithDrawAccount(Constant.user.getToken(),id,1)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 编辑提现账户
     * @param withDrawAccountDto
     * @return
     */
    public Flowable<String> updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto){
        return getService().updateWithDrawAccount(Constant.user.getToken(),
                withDrawAccountDto.getId(),
                withDrawAccountDto.getName(),withDrawAccountDto.getWithdrawPhone(),
                withDrawAccountDto.getTypeName(),withDrawAccountDto.getWithdrawNumber())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取提现账户列表
     * @return
     */
    public Flowable<List<WithDrawAccountDto>> getWithDrawAccount(){
        return getService().getWithDrawAccount(Constant.user.getToken())
                .compose(this.<List<WithDrawAccountDto>>applySchedulers());
    }

    /**
     * 提现
     * @param withDrawAccountDto
     * @param money
     * @return
     */
    public Flowable<String> submitWithDraw(WithDrawAccountDto withDrawAccountDto,String money){
        return getService().submitWithDraw(Constant.user.getToken(),withDrawAccountDto.getId(),money)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取商城的 商品列表
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsDto>> getGoodsListOfShopping(int pageNo){
        return getService().getGoodsListOfShopping(pageNo,"")
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取商家发布的商品列表
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsDto>> getGoodsListOfShop(int pageNo){
        return getService().getGoodsListOfShop(Constant.user.getToken(),pageNo)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取商品详情
     * @param productNo
     * @return
     */
    public Flowable<GoodsDetailsDto> getGoodsDetails(String productNo){
        return getService().getGoodsDetails(Constant.user.getToken(),productNo)
                .compose(this.<GoodsDetailsDto>applySchedulers());
    }

    /**
     * 关注商品
     * @param productNo
     * @return
     */
    public Flowable<String> followGodos(String productNo){
        return getService().followGoods(Constant.user.getToken(),productNo)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 发布商品
     * @param goodsDetailsDto
     * @return
     */
    public Flowable<String> createNewGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().createNewGoods(Constant.user.getToken(),goodsDetailsDto.getName(),goodsDetailsDto.getSubtitle(),
                goodsDetailsDto.getPrice(),goodsDetailsDto.getProductBannerUrl(),
                goodsDetailsDto.getFreightInMaxCount(),goodsDetailsDto.getFreightInEveryAdd(),
                goodsDetailsDto.getProductDetail(),goodsDetailsDto.getBannerUrl(),goodsDetailsDto.getIsInIndexActivity())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取会员信息
     * @return
     */
    public Flowable<List<MemberTypeDto>> getMemberTypeInfo(){
        return getService().getMemberTypeInfo(Constant.user.getToken())
                .compose(this.<List<MemberTypeDto>>applySchedulers());
    }

    /**
     * 创建支付订单
     * @param amount
     * @return
     */
    public Flowable<PayOrderDto> createPayOrder(String amount){
        return getService().createPayOrder(Constant.user.getToken(),amount)
                .compose(this.<PayOrderDto>applySchedulers());
    }

    /**
     * 获取支付宝支付相应参数
     * @param payNumber
     * @return
     */
    public Flowable<String> getAlipayParams(String payNumber){
        return getService().getAlipayParams(Constant.user.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取微信支付相应参数
     * @param payNumber
     * @return
     */
    public Flowable<WeiCharParamsDto> getWeiChatParams(String payNumber){
        return getService().getWeiChatParams(Constant.user.getToken(),payNumber)
                .compose(this.<WeiCharParamsDto>applySchedulers());
    }
}
