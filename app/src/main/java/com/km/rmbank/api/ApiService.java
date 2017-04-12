package com.km.rmbank.api;

import com.km.rmbank.dto.AlipayParamsDto;
import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.MemberTypeDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.Response;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.entity.OrderEntity;
import com.km.rmbank.utils.retrofit.SecretConstant;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
//import rx.Observable;

/**
 * Created by kamangkeji on 17/2/10.
 */

public interface ApiService {

    /**
     * 登录
     *
     * @param mobilePhone
     * @param loginPwd
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/user/login")
    Flowable<Response<UserDto>> login(@Field("mobilePhone") String mobilePhone,
                                      @Field("loginPwd") String loginPwd);

    /**
     * 注册
     *
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/user/register")
    Flowable<Response<DefaultDto>> userRegister(@Field("mobilePhone") String mobilePhone,
                                                @Field("loginPwd") String password,
                                                @Field("smsCode") String smsCode);

    /**
     * 获取手机验证码
     *
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/sms/send/code")
    Flowable<Response<DefaultDto>> getPhoneCode(@Field("mobilePhone") String mobilePhone);

    /**
     * 忘记密码
     *
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/user/forgetLoginPwd")
    Flowable<Response<DefaultDto>> forgetLoginPwd(@Field("mobilePhone") String mobilePhone,
                                                  @Field("pwd") String password,
                                                  @Field("smsCode") String smsCode);


    /**
     * 个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/info")
    Flowable<Response<UserInfoDto>> getUserInfo(@Field("token") String token);

    /**
     * 修改个人信息
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/update/info")
    Flowable<Response<String>> updateUserInfo(@Field("token") String token,
                                              @Field("nickName") String nickName,
                                              @Field("portraitUrl") String portraitUrl,
                                              @Field("birthday") String birthday);

    /**
     * 生成个人名片
     *
     * @param token
     * @param name
     * @param mobilePhone
     * @param company
     * @param position
     * @param companyProfile
     * @param provideResourcesId
     * @param demandResourcesId
     * @param location
     * @param detailedAddress
     * @param emailAddress
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/userCard/update/info")
    Flowable<Response<UserCardDto>> createUserCard(@Field("token") String token,
                                                   @Field("name") String name,
                                                   @Field("cardPhone") String mobilePhone,
                                                   @Field("company") String company,
                                                   @Field("position") String position,
                                                   @Field("companyProfile") String companyProfile,
                                                   @Field("provideResourcesId") String provideResourcesId,
                                                   @Field("demandResourcesId") String demandResourcesId,
                                                   @Field("location") String location,
                                                   @Field("detailedAddress") String detailedAddress,
                                                   @Field("emailAddress") String emailAddress);

    /**
     * 获取个人名片
     *
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/userCard/info")
    Flowable<Response<UserCardDto>> getUserCard(@Field("token") String token);

    /**
     * 获取行业数据
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/list/industry")
    Flowable<Response<List<IndustryDto>>> getIndustryList(@Field("token") String token);


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST(SecretConstant.API_HOST_PATH + "/file/up")
    Flowable<Response<String>> imageUpload(@Part("optionType") RequestBody optionType,
                                           @Part MultipartBody.Part file);

    /**
     * 查询账户余额
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/my/account")
    Flowable<Response<UserBalanceDto>> getUserAccountBalance(@Field("token") String token);

    /**
     * 查询账户明细
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/account/user/stream/list")
    Flowable<Response<List<UserAccountDetailDto>>> getUserAccountDetail(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);


    /**
     * 新增提现账户
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/withdraw/add/account")
    Flowable<Response<String>> createWithDrawAccount(@Field("token") String token,
                                                     @Field("name") String name,
                                                     @Field("withdrawPhone") String withdrawPhone,
                                                     @Field("typeName") String typeName,
                                                     @Field("withdrawNumber") String withdrawNumber);


    /**
     * 删除提现账户
     *
     * @param token
     * @param id
     * @param delete
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/withdraw/delete/account")
    Flowable<Response<String>> deleteWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("delete") int delete);

    /**
     * 编辑提现账户
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/withdraw/update/account")
    Flowable<Response<String>> updateWithDrawAccount(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("name") String name,
                                                     @Field("withdrawPhone") String withdrawPhone,
                                                     @Field("typeName") String typeName,
                                                     @Field("withdrawNumber") String withdrawNumber);

    /**
     * 获取提现账户列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/withdraw/list/account")
    Flowable<Response<List<WithDrawAccountDto>>> getWithDrawAccount(@Field("token") String token);

    /**
     * 提现
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/withdraw")
    Flowable<Response<String>> submitWithDraw(@Field("token") String token,
                                              @Field("accountId") String accountId,
                                              @Field("userAmount") String userAmount);


    /**
     * 获取商品列表  商城
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/normal/list")
    Flowable<Response<List<GoodsDto>>> getGoodsListOfShopping(@Field("pageNo") int pageNo,
                                                              @Field("isInIndexActivity") String isInIndexActivity);

    /**
     * 商家列表的商品列表
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/shop/list")
    Flowable<Response<List<GoodsDto>>> getGoodsListOfShop(@Field("token") String token,
                                                          @Field("pageNo") int pageNo);

    /**
     * 获取商品详情
     *
     * @param productNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/productDetail")
    Flowable<Response<GoodsDetailsDto>> getGoodsDetails(@Field("token") String token,
                                                        @Field("productNo") String productNo);

    /**
     * 关注商品
     *
     * @param productNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/keep/product")
    Flowable<Response<String>> followGoods(@Field("token") String token,
                                           @Field("productNo") String productNo);

    /**
     * 发布商品
     *
     * @param token
     * @param productName
     * @param subtitle
     * @param price
     * @param productBanner
     * @param freightInMaxCount
     * @param freightInEveryAdd
     * @param productDetail
     * @param bannerUrl
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/add")
    Flowable<Response<String>> createNewGoods(@Field("token") String token,
                                              @Field("name") String productName,
                                              @Field("subtitle") String subtitle,
                                              @Field("price") String price,
                                              @Field("productBanner") String productBanner,
                                              @Field("freightInMaxCount") String freightInMaxCount,
                                              @Field("freightInEveryAdd") String freightInEveryAdd,
                                              @Field("productDetail") String productDetail,
                                              @Field("bannerUrl") String bannerUrl,
                                              @Field("isInIndexActivity") String isInIndexActivity);


    /**
     * 获取会员对应类型的金额
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/user/joinMember/money")
    Flowable<Response<List<MemberTypeDto>>> getMemberTypeInfo(@Field("token") String token);


    /**
     * 创建支付订单
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/recharge/create")
    Flowable<Response<PayOrderDto>> createPayOrder(@Field("token") String token,
                                                   @Field("amount") String amount);


    /**
     * 获取支付宝订单信息参数
     *
     * @param payNumber
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/alipay/get/params")
    Flowable<Response<String>> getAlipayParams(@Field("token") String token,
                                               @Field("payNumber") String payNumber);

    /**
     * 获取微信订单信息参数
     *
     * @param payNumber
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/wx/pay/create/pre/payment/order")
    Flowable<Response<WeiCharParamsDto>> getWeiChatParams(@Field("token") String token,
                                                          @Field("payNumber") String payNumber);

    /**
     * 新增收货地址
     *
     * @param token
     * @param receivePerson
     * @param receivePersonPhone
     * @param receiveAddress
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/add")
    Flowable<Response<String>> newReceiverAddress(@Field("token") String token,
                                                  @Field("receivePerson") String receivePerson,
                                                  @Field("receivePersonPhone") String receivePersonPhone,
                                                  @Field("receiveAddress") String receiveAddress);

    /**
     * 编辑收货地址
     *
     * @param token
     * @param receivePerson
     * @param receivePersonPhone
     * @param receiveAddress
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/update")
    Flowable<Response<String>> updateReceiverAddress(@Field("token") String token,
                                                     @Field("id") String id,
                                                     @Field("receivePerson") String receivePerson,
                                                     @Field("receivePersonPhone") String receivePersonPhone,
                                                     @Field("receiveAddress") String receiveAddress);

    /**
     * 获取收货地址列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/list")
    Flowable<Response<List<ReceiverAddressDto>>> getReceiverAddressList(@Field("token") String token);

    /**
     * 设置默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/update/default")
    Flowable<Response<String>> setDefaultReceiverAddress(@Field("token") String token,
                                                         @Field("id") String id);

    /**
     * 删除收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/delete")
    Flowable<Response<String>> deleteReceiverAddress(@Field("token") String token,
                                                         @Field("id") String id);

    /**
     * 获取默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/receive/address/get/default")
    Flowable<Response<ReceiverAddressDto>> getDefaultReceiverAddress(@Field("token") String token);


    /**
     * 获取默认收货地址
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/shop/car/add")
    Flowable<Response<String>> addShoppingCart(@Field("token") String token,
                                                           @Field("productNo") String productNo,
                                                           @Field("count") String count);

    /**
     * 获取购物车列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/shop/car/list")
    Flowable<Response<List<ShoppingCartDto>>> getShoppingCartList(@Field("token") String token);

    /**
     * 购物车 去结算 创建订单
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/go/shopList")
    Flowable<Response<List<ShoppingCartDto>>> createOrder(@Field("token") String token,
                                                          @Field("productNos") String productNos);

    /**
     * 更新购物车商品的数量
     * productNo 订单编号
     * optionType 请求类型1增加2减少
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/shop/car/update/count")
    Flowable<Response<String>> updateCountOnShopCartForGoods(@Field("token") String token,
                                                             @Field("productNo") String productNo,
                                                             @Field("optionType") String optionType);


    /**
     * 提交订单
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/create")
    Flowable<Response<PayOrderDto>> submitOrder(@Field("token") String token,
                                                          @Field("productNos") String productNos,
                                                          @Field("productCounts") String productCounts,
                                                          @Field("receiveAddressId") String receiveAddressId,
                                                          @Field("freight") String freight,
                                                @Field("exchange") String exchange);

    /**
     * 获取订单列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/list")
    Flowable<Response<List<OrderEntity>>> getOrderList(@Field("token") String token,
                                                       @Field("type") String type,
                                                       @Field("pageNo") int pageNo);


}
