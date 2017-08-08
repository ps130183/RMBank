package com.km.rmbank.api;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.AppVersionDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.dto.HomeNewRecommendDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.dto.MyFriendsDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.NearbyVipDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ServiceDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.dto.OrderDto;
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
     * @param smsCode
     * @return
     */
    public Flowable<UserDto> login(String mobilePhone, String smsCode){
        return getService().login(mobilePhone,smsCode).compose(this.<UserDto>applySchedulers());
    }

    /**
     * 注册
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Flowable<String> userRegister(String mobilePhone,String loginPwd,String smsCode){
        return getService().userRegister(mobilePhone,loginPwd,smsCode).compose(this.<String>applySchedulers());
    }


    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    public Flowable<String> getPhoneCode(String mobilePhone){
        return getService().getPhoneCode(mobilePhone).compose(this.<String>applySchedulers());
    }

    /**
     * 忘记密码
     * @param mobilePhone
     * @param loginPwd
     * @param smsCode
     * @return
     */
    public Flowable<String> forgetLoginPwd(String mobilePhone,String loginPwd,String smsCode){
        return getService().forgetLoginPwd(mobilePhone,loginPwd,smsCode).compose(this.<String>applySchedulers());
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
    public Flowable<String> createUserCart(UserCardDto userCardDto){
        return getService().createUserCard(Constant.user.getToken()
                ,userCardDto.getName()
                ,userCardDto.getCardPhone()
                ,userCardDto.getPosition()
                ,userCardDto.getProvideResources()
                ,userCardDto.getDemandResources()
                ,userCardDto.getDetailedAddress()
                ,userCardDto.getEmailAddress())
                .compose(this.<String>applySchedulers());
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
//        Bitmap mBitmap = ImageUtils.compressByQuality(ImageUtils.getBitmap(imagePath),(long)(2 * 1024 * 1024),true);
//        image = ImageUtils.compressImage(ImageUtils.getBitmap(imagePath));
//        image = mBitmap.
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
    public Flowable<List<GoodsDto>> getGoodsListOfShopping(int pageNo,String typeId){
        return getService().getGoodsListOfShopping(pageNo,typeId)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取商城的 商品列表
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsDto>> getGoodsListOfShoppingNew(int pageNo,String typeId,int orderBy,String roleId){
        return getService().getGoodsListOfShoppingNew(pageNo,typeId,orderBy,roleId)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取商城的 搜索
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsDto>> getGoodsListOfSearch(int pageNo,String name){
        return getService().getGoodsListOfSearch(pageNo,name)
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
    public Flowable<String> followGodos(String productNo,String clubId){
        return getService().followGoods(Constant.user.getToken(),productNo,clubId)
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
     * 修改商品
     * @param goodsDetailsDto
     * @return
     */
    public Flowable<String> updateGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().updateGoods(Constant.user.getToken(),goodsDetailsDto.getProductNo(),goodsDetailsDto.getName(),goodsDetailsDto.getSubtitle(),
                goodsDetailsDto.getPrice(),goodsDetailsDto.getProductBannerUrl(),
                goodsDetailsDto.getFreightInMaxCount(),goodsDetailsDto.getFreightInEveryAdd(),
                goodsDetailsDto.getProductDetail(),goodsDetailsDto.getBannerUrl(),goodsDetailsDto.getIsInIndexActivity())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 商品管理 修改商品前 获取商品信息
     * @param productNo
     * @return
     */
    public Flowable<GoodsDetailsDto> getGoodsInfo(String productNo){
        return getService().getGoodsInfo(Constant.user.getToken(),productNo)
                .compose(this.<GoodsDetailsDto>applySchedulers());
    }

    /**
     * 商品下架
     * @param productNo
     * @return
     */
    public Flowable<String> goodsSlodOut(String productNo){
        return getService().goodsSoldOut(Constant.user.getToken(),productNo)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取已售出 商品列表
     * @param pageNo
     * @return
     */
    public Flowable<List<OrderDto>> getSellGoodsList(int pageNo){
        return getService().getSellGoodsList(Constant.user.getToken(),pageNo)
                .compose(this.<List<OrderDto>>applySchedulers());
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
    public Flowable<PayOrderDto> createPayOrder(String amount,String memberType){
        return getService().createPayOrder(Constant.user.getToken(),amount,memberType)
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

    /**
     * 新增收货地址
     * @param receiverAddressDto
     * @return
     */
    public Flowable<String> newReceiverAddress(ReceiverAddressDto receiverAddressDto){
        return getService().newReceiverAddress(Constant.user.getToken(),
                receiverAddressDto.getReceivePerson(),receiverAddressDto.getReceivePersonPhone(),
                receiverAddressDto.getReceiveAddress()).compose(this.<String>applySchedulers());
    }

    /**
     * 编辑收货地址
     * @param receiverAddressDto
     * @return
     */
    public Flowable<String> updateReceiverAddress(ReceiverAddressDto receiverAddressDto){
        return getService().updateReceiverAddress(Constant.user.getToken(),
                receiverAddressDto.getId(),receiverAddressDto.getReceivePerson(),
                receiverAddressDto.getReceivePersonPhone(),receiverAddressDto.getReceiveAddress())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取收货地址列表
     * @return
     */
    public Flowable<List<ReceiverAddressDto>> getReceiverAddressList(){
        return getService().getReceiverAddressList(Constant.user.getToken())
                .compose(this.<List<ReceiverAddressDto>>applySchedulers());
    }

    /**
     * 设置默认收货地址
     * @param id
     * @return
     */
    public Flowable<String> setDefaultReceiverAddress(String id){
        return getService().setDefaultReceiverAddress(Constant.user.getToken(),id)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    public Flowable<String> deleteReceiverAddress(String id){
        return getService().deleteReceiverAddress(Constant.user.getToken(),id)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取默认收货地址
     * @return
     */
    public Flowable<ReceiverAddressDto> getDefaultReceiverAddress(){
        return getService().getDefaultReceiverAddress(Constant.user.getToken())
                .compose(this.<ReceiverAddressDto>applySchedulers());
    }

    /**
     * 加入购物车
     * @param productNo
     * @param count
     * @return
     */
    public Flowable<String> addShoppingCart(String productNo,String count){
        return getService().addShoppingCart(Constant.user.getToken(),productNo,count)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取购物车列表
     * @return
     */
    public Flowable<List<ShoppingCartDto>> getShoppingCartList(){
        return getService().getShoppingCartList(Constant.user.getToken())
                .compose(this.<List<ShoppingCartDto>>applySchedulers());
    }

    /**
     * 删除购物车商品
     * @param goodsDetailsDto
     * @return
     */
    public Flowable<String> deleteShoppingCartGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().deleteShoppingCartGoods(Constant.user.getToken(),goodsDetailsDto.getProductNo())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 购物车 去结算 创建订单
     * @param productNo
     * @return
     */
    public Flowable<List<ShoppingCartDto>> createOrder(String productNo){
        return getService().createOrder(Constant.user.getToken(),productNo)
                .compose(this.<List<ShoppingCartDto>>applySchedulers());
    }

    /**
     * 更新购物车商品的数量
     * @param productNo
     * @param optionType
     * @return
     */
    public Flowable<String> updateCountOnShopCartForGoods(String productNo,String optionType){
        return getService().updateCountOnShopCartForGoods(Constant.user.getToken(),productNo,optionType)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 提交订单
     * @param productNos
     * @param productCounts
     * @param receiveAddressId
     * @param freight
     * @return
     */
    public Flowable<PayOrderDto> submitOrder(String productNos,String productCounts,String receiveAddressId,
                                             String freight,String exchange){
        return getService().submitOrder(Constant.user.getToken(),productNos,productCounts,
                receiveAddressId,freight,exchange).compose(this.<PayOrderDto>applySchedulers());
    }

    /**
     * 获取我的订单列表
     * @param type
     * @param pageNo
     * @return
     */
    public Flowable<List<OrderDto>> getOrderList(String type, int pageNo){
        return getService().getOrderList(Constant.user.getToken(),type,pageNo)
                .compose(this.<List<OrderDto>>applySchedulers());
    }

    /**
     * 获取商品分类
     * @return
     */
    public Flowable<List<GoodsTypeDto>> getGoodsTypes(){
        return getService().getGoodsTypes(Constant.user.getToken())
                .compose(this.<List<GoodsTypeDto>>applySchedulers());
    }

    /**
     * 获取新版 商城 商品分类
     * @return
     */
    public Flowable<List<HomeGoodsTypeDto>> getGoodsType(){
        return getService().getGoodsType("1")
                .compose(this.<List<HomeGoodsTypeDto>>applySchedulers());
    }

    /**
     * 获取 新增商品 选择 商品分类
     * @return
     */
    public Flowable<List<HomeGoodsTypeDto>> getGoodsTypeForCreateGoods(){
        return getService().getGoodsTypeForCreateGoods("1")
                .compose(this.<List<HomeGoodsTypeDto>>applySchedulers());
    }

    /**
     * 获取关注商品列表
     * @param pageNo
     * @return
     */
    public Flowable<List<GoodsDto>> getAttentionGoodsList(int pageNo){
        return getService()
                .getAttentionGoodsList(Constant.user.getToken(),pageNo)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取活动列表
     * @param pageNo
     * @return
     */
    public Flowable<List<ActionDto>> getActionList(int pageNo){
        return getService().getActionList(pageNo)
                .compose(this.<List<ActionDto>>applySchedulers());
    }

    /**
     * 获取资讯列表
     * @param pageNo
     * @return
     */
    public Flowable<List<InformationDto>> getInformationList(int pageNo){
        return getService().getInformationList(pageNo)
                .compose(this.<List<InformationDto>>applySchedulers());
    }

    /**
     * 获取首页冬天  平台发布的资讯
     * @param pageNo
     * @return
     */
    public Flowable<List<InformationDto>> getDynamicInformationList(int pageNo){
        return getService().getDynamicInformationList(pageNo)
                .compose(this.<List<InformationDto>>applySchedulers());
    }

    /**
     * 获取资讯详情
     * @param id
     * @return
     */
    public Flowable<String> getInformationDetail(String id){
        return getService().getInformationDetail(id)
                .compose(this.<String>applySchedulers());
    }
    /**
     * 获取首页活动 商品推荐
     * @return
     */
    public Flowable<List<HomeRecommendDto>> getHomeRecommend(int pageNo){
        return getService().getHomeActionRecommend(pageNo)
                .compose(this.<List<HomeRecommendDto>>applySchedulers());
    }

    /**
     * 获取 新版 首页活动 商品推荐
     * @return
     */
    public Flowable<List<HomeNewRecommendDto>> getHomeNewRecommend(int pageNo){
        return getService().getHomeNewActionRecommend(pageNo)
                .compose(this.<List<HomeNewRecommendDto>>applySchedulers());
    }

    /**
     * 获取首页消息
     * @param pageNo
     * @return
     */
    public Flowable<List<MessageDto>> getMessage(int pageNo){
        return getService().getMessage(Constant.user.getToken(),pageNo)
                .compose(this.<List<MessageDto>>applySchedulers());
    }

    /**
     * 余额支付
     * @param payNumber
     * @return
     */
    public Flowable<String> payBalance(String payNumber){
        return getService().payBalance(Constant.user.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 我的订单 去支付
     * @param orderNo
     * @return
     */
    public Flowable<PayOrderDto> toPayOnMyOrder(String orderNo){
        return getService().toPayOnMyOrder(Constant.user.getToken(),orderNo)
                .compose(this.<PayOrderDto>applySchedulers());
    }

    /**
     * 商家发货
     * @param orderNo
     * @param expressCompany
     * @param expressNumber
     * @return
     */
    public Flowable<String> sendGoods(String orderNo,String expressCompany,String expressNumber){
        return getService().sendGoods(Constant.user.getToken(),orderNo,expressCompany,expressNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取订单详情
     * @param orderDto
     * @return
     */
    public Flowable<OrderDto> getOrderDetails(OrderDto orderDto){
        return getService().getOrderDetails(Constant.user.getToken(),orderDto.getOrderNo())
                .compose(this.<OrderDto>applySchedulers());
    }

    /**
     * 确认收货
     * @param orderDto
     * @return
     */
    public Flowable<String> confirmReceiverGoods(OrderDto orderDto){
        return getService().confirmReceiverGoods(Constant.user.getToken(),orderDto.getOrderNo())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 发表评论
     * @param orderNo
     * @param content
     * @return
     */
    public Flowable<String> evaluateGoods(String orderNo,String content){
        return getService().evaluateGoods(Constant.user.getToken(),orderNo,content)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取评论列表
     * @param productNo
     * @param pageNo
     * @return
     */
    public Flowable<List<EvaluateDto>> getEvaluateList(String productNo,int pageNo){
        return getService().getEvaluateList(Constant.user.getToken(),productNo,pageNo)
                .compose(this.<List<EvaluateDto>>applySchedulers());
    }

    /**
     * 获取我的积分
     * @return
     */
    public Flowable<IntegralDto> getIntegralInfo(){
        return getService().getIntegralInfo(Constant.user.getToken())
                .compose(this.<IntegralDto>applySchedulers());
    }

    /**
     * 获取积分明细列表
     * @param pageNo
     * @return
     */
    public Flowable<List<IntegralDetailsDto>> getIntegralDetailsList(int pageNo){
        return getService().getIntegralDetailsList(Constant.user.getToken(),pageNo)
                .compose(this.<List<IntegralDetailsDto>>applySchedulers());
    }

    /**
     * 扫一扫 二维码
     * @param url
     * @return
     */
    public Flowable<UserCardDto> getUserCardOnQRCode(String url){
        return getService().getUserInfoOnQRCode(url,Constant.user.getToken())
                .compose(this.<UserCardDto>applySchedulers());
    }

    /**
     * 申请成为好友
     * @param friendPhone
     * @return
     */
    public Flowable<String> applyBecomeFriend(String friendPhone){
        return getService().applyBecomeFriend(Constant.user.getToken(),friendPhone)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取我的人脉列表
     * @return
     */
    public Flowable<List<MyFriendsDto>> getMyFriends(){
        return getService().getMyFriends(Constant.user.getToken())
                .compose(this.<List<MyFriendsDto>>applySchedulers());
    }
    /**
     * 获取分享的内容
     * @return
     */
    public Flowable<ShareDto> getShareContent(){
        return getService().getShareContent(Constant.user.getToken())
                .compose(this.<ShareDto>applySchedulers());
    }

    /**
     * 获取我的团队数据
     * @return
     */
    public Flowable<List<MyTeamDto>> getMyTeam(){
        return getService().getMyTeam(Constant.user.getToken())
                .compose(this.<List<MyTeamDto>>applySchedulers());
    }

    /**
     * 通过id获取用户的名片信息
     * @param id
     * @return
     */
    public Flowable<UserCardDto> getUserCardById(String id){
        return getService().getUserCardById(Constant.user.getToken(),id)
                .compose(this.<UserCardDto>applySchedulers());
    }

    /**
     * 检测是否有新版本
     * @param appVersion
     * @return
     */
    public Flowable<AppVersionDto> checkAppVersion(int appVersion){
        return getService().checkAppVersion(appVersion)
                .compose(this.<AppVersionDto>applySchedulers());
    }

    /**
     * 支付回调验证
     * @param payNumber
     * @return
     */
    public Flowable<String> checkPayResult(String payNumber){
        return getService().checkPayResult(Constant.user.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取首页banner
     * @return
     */
    public Flowable<List<BannerDto>> getHomeBanner(){
        return getService().getHomeBanner("")
                .compose(this.<List<BannerDto>>applySchedulers());
    }
    /**
     * 获取资讯页banner
     * @return
     */
    public Flowable<List<InformationDto>> getInformationBanner(){
        return getService().getInformationBanner("")
                .compose(this.<List<InformationDto>>applySchedulers());
    }


    /**
     * 获取 新版首页 商品一级分类
     * @return
     */
    public Flowable<List<HomeGoodsTypeDto>> getHomeGoodsTypes(){
        return getService().getHomeGoodsType("1")
                .compose(this.<List<HomeGoodsTypeDto>>applySchedulers());
    }

    /**
     * 更新用户的位置信息
     * @param longitude
     * @param latitude
     * @return
     */
    public Flowable<String> updateUserLocation(String longitude,String latitude){
        return getService().updateUserLocation(Constant.user.getToken(),longitude,latitude)
                .compose(this.<String>applySchedulers());
    }


    /**
     * 获取附近合伙人信息
     * @param pageNo
     * @return
     */
    public Flowable<List<NearbyVipDto>> getNearbyVip(int pageNo){
        return getService().getNearbyVip(Constant.user.getToken(),pageNo)
                .compose(this.<List<NearbyVipDto>>applySchedulers());
    }

    /**
     * 更新 是否允许其他人查看 名片的状态
     * @param allowStatus
     * @return
     */
    public Flowable<String> updateAllowUserCard(String allowStatus){
        return getService().updateAllowUserCard(Constant.user.getToken(),allowStatus)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取俱乐部信息
     * @param type
     * @return
     */
    public Flowable<List<ClubDto>> getClubInfos(String type){
        return getService().getClubInfos(type)
                .compose(this.<List<ClubDto>>applySchedulers());
    }

    /**
     * 创建我的俱乐部
     * @param clubDto
     * @return
     */
    public Flowable<String> createMyClub(ClubDto clubDto){
        String clubDetailList = gson.toJson(clubDto.getClubDetailList());
        return getService().createMyClub(Constant.user.getToken(),
                clubDto.getClubName(),
                clubDto.getClubLogo(),
                clubDto.getContent(),
                clubDto.getBackgroundImg(),
                clubDetailList)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 创建我的俱乐部
     * @param clubDto
     * @return
     */
    public Flowable<String> editMyClub(ClubDto clubDto){
        String clubDetailList = gson.toJson(clubDto.getClubDetailList());
        return getService().editMyClub(Constant.user.getToken(),
                clubDto.getClubName(),
                clubDto.getClubLogo(),
                clubDto.getContent(),
                clubDto.getBackgroundImg(),
                clubDetailList,
                clubDto.getId())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取我的俱乐部信息
     * @return
     */
    public Flowable<ClubDto> getMyClubInfo(String clubId){
        return getService().getMyClubInfoBasic(Constant.user.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }

    /**
     * 获取我的俱乐部的 图文介绍 信息
     * @param clubId
     * @return
     */
    public Flowable<List<ClubDto.ClubDetailBean>> getMyClubInfoDetails(String clubId){
        return getService().getMyClubInfoDetails(clubId)
                .compose(this.<List<ClubDto.ClubDetailBean>>applySchedulers());
    }

    /**
     * 俱乐部 发布 活动
     * @param actionDto
     * @return
     */
    public Flowable<String> releaseActionRecent(ActionDto actionDto,String clubId){
        String guestList = gson.toJson(actionDto.getGuestList());
        return getService().releaseActionRecent(Constant.user.getToken(),
                clubId,
                actionDto.getActivityPictureUrl(),
                actionDto.getTitle(),
                actionDto.getAddress(),
                actionDto.getFlow(),
                actionDto.getHoldDate(),
                guestList)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取俱乐部 近期活动列表
     * @param clubId
     * @param pageNo
     * @return
     */
    public Flowable<List<ActionDto>> getActionRecentList(String clubId,int pageNo){
        return getService().getActionRecentList(clubId,pageNo)
                .compose(this.<List<ActionDto>>applySchedulers());
    }

    /**
     * 获取俱乐部 发布近期活动的详细细腻
     * @param actionId
     * @return
     */
    public Flowable<ActionDto> getActionRecentInfo(String actionId){
        return getService().getActionRecentInfo(actionId)
                .compose(this.<ActionDto>applySchedulers());
    }

    /**
     * 获取俱乐部 活动 的参加人员 列表
     * @param actionId
     * @return
     */
    public Flowable<List<ActionMemberDto>> getActionMemberList(String actionId,int pageNo){
        return getService().getActionMemberList(actionId,pageNo)
                .compose(this.<List<ActionMemberDto>>applySchedulers());
    }

    /**
     * 俱乐部 发布 往期活动
     * @param actionPastDto
     * @return
     */
    public Flowable<String> releaseActionPast(ActionPastDto actionPastDto){
        String dynamicList = gson.toJson(actionPastDto.getDynamicList());
        return getService().releaseActionPast(actionPastDto.getClubId(),
                actionPastDto.getAvatarUrl(),
                actionPastDto.getTitle(),dynamicList)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取往期资讯列表
     * @param clubId
     * @param pageNo
     * @return
     */
    public Flowable<List<ActionPastDto>> getActionPastList(String clubId,int pageNo){
        return getService().getActionPastList(clubId,pageNo)
                .compose(this.<List<ActionPastDto>>applySchedulers());
    }

    /**
     * 获取往期资讯 详情
     * @param id
     * @return
     */
    public Flowable<ActionPastDto> getActionPastDetail(String id){
        return getService().getActionPastDetail(id)
                .compose(this.<ActionPastDto>applySchedulers());
    }

    /**
     * 获取 首页约吗  数据 未举办活动
     * @param pageNo
     * @return
     */
    public Flowable<List<ActionDto>> getActionRecentList(int pageNo){
        return getService().getActionRecentList(pageNo)
                .compose(this.<List<ActionDto>>applySchedulers());
    }

    /**
     * 活动报名
     * @param name
     * @param phone
     * @return
     */
    public Flowable<String> applyAction(String activityId,String name,String phone){
        return getService().applyAction(Constant.user.getToken(),activityId,name,phone)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取客服信息
     * @return
     */
    public Flowable<ServiceDto> getServiceInfo(){
        return getService().getServiceInfo(Constant.user.getToken())
                .compose(this.<ServiceDto>applySchedulers());
    }
}
