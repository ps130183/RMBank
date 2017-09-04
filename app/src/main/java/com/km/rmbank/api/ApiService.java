package com.km.rmbank.api;

import android.support.annotation.IdRes;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ActionMemberNumDto;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.AppVersionDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
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
import com.km.rmbank.dto.Response;
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
import com.km.rmbank.utils.retrofit.SecretConstant;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
//import rx.Observable;

/**
 * Created by kamangkeji on 17/2/10.
 */

public interface ApiService {



    /**
     * 登录
     *
     * @param mobilePhone
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/user/registerAndLogin")
    Flowable<Response<UserDto>> login(@Field("mobilePhone") String mobilePhone,
                                      @Field("smsCode") String smsCode);

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
    Flowable<Response<String>> userRegister(@Field("mobilePhone") String mobilePhone,
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
    Flowable<Response<String>> getPhoneCode(@Field("mobilePhone") String mobilePhone);

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
    Flowable<Response<String>> forgetLoginPwd(@Field("mobilePhone") String mobilePhone,
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
     * @param position
     * @param detailedAddress
     * @param emailAddress
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/userCard/update/info")
    Flowable<Response<String>> createUserCard(@Field("token") String token,
                                                   @Field("name") String name,
                                                   @Field("cardPhone") String mobilePhone,
                                                   @Field("position") String position,
                                                   @Field("provideResources") String provideResources,
                                                   @Field("demandResources") String demandResources,
                                                   @Field("detailedAddress") String detailedAddress,
                                                   @Field("emailAddress") String emailAddress);

    /**
     * 获取个人名片
     *
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/userCard/info/send")
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
     * 获取商品列表  商城
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/normal/list")
    Flowable<Response<List<GoodsDto>>> getGoodsListOfShoppingNew(@Field("pageNo") int pageNo,
                                                              @Field("isInIndexActivity") String isInIndexActivity,
                                                                 @Field("orderBy") int orderBy,
                                                                 @Field("roleId") String roleId);

    /**
     * 获取商品列表  搜索
     *
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/normal/search")
    Flowable<Response<List<GoodsDto>>> getGoodsListOfSearch(@Field("pageNo") int pageNo,
                                                            @Field("name") String name);

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
                                           @Field("productNo") String productNo,
                                           @Field("clubId") String clubId);

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
     * 商品修改
     *
     * @param token
     * @param productNo
     * @param productName
     * @param subtitle
     * @param price
     * @param productBanner
     * @param freightInMaxCount
     * @param freightInEveryAdd
     * @param productDetail
     * @param bannerUrl
     * @param isInIndexActivity
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/edit")
    Flowable<Response<String>> updateGoods(@Field("token") String token,
                                           @Field("productNo") String productNo,
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
     * 商品 管理  修改商品前 获取商品信息
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/editView")
    Flowable<Response<GoodsDetailsDto>> getGoodsInfo(@Field("token") String token,
                                                     @Field("productNo") String productNo);

    /**
     * 商品 管理  下架
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/product/normal/soldOut")
    Flowable<Response<String>> goodsSoldOut(@Field("token") String token,
                                            @Field("productNo") String productNo);


    ///auth/order/buy/shop/list

    /**
     * 商品 管理  已售出
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/shop/list")
    Flowable<Response<List<OrderDto>>> getSellGoodsList(@Field("token") String token,
                                                        @Field("pageNo") int pageNo);

    /**
     * 获取会员对应类型的金额
     *
     * @param token
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/member/joinMember/money")
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
                                                   @Field("amount") String amount,
                                                   @Field("memberType") String memberType);


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
     * 删除购物车 商品
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/shop/car/delete")
    Flowable<Response<String>> deleteShoppingCartGoods(@Field("token") String token,
                                                       @Field("productNos") String productNos);

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
     *
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
    Flowable<Response<List<OrderDto>>> getOrderList(@Field("token") String token,
                                                    @Field("type") String type,
                                                    @Field("pageNo") int pageNo);

    /**
     * 获取商品类型
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/normal/type")
    Flowable<Response<List<GoodsTypeDto>>> getGoodsTypes(@Field("token") String token);

    /**
     * 获取  新版  商城页面  商品分类
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/productTypes/listpage")
    Flowable<Response<List<HomeGoodsTypeDto>>> getGoodsType(@Field("type") String type);

    /**
     * 获取  新增商品  选择  商品分类
     *
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/productTypes/list")
    Flowable<Response<List<HomeGoodsTypeDto>>> getGoodsTypeForCreateGoods(@Field("type") String type);

    /**
     * 关注商品列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/keep/product/list")
    Flowable<Response<List<GoodsDto>>> getAttentionGoodsList(@Field("token") String token,
                                                             @Field("pageNo") int pageNo);

    /**
     * 获取活动列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/activity/list")
    Flowable<Response<List<ActionDto>>> getActionList(@Field("pageNo") int pageNo);

    /**
     * 获取资讯列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/list")
    Flowable<Response<List<InformationDto>>> getInformationList(@Field("pageNo") int pageNo);

    /**
     *   获取首页 动态  平台发布的资讯
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/managerDynamic")
    Flowable<Response<List<InformationDto>>> getDynamicInformationList(@Field("pageNo") int pageNo);

    /**
     * 获取资讯列表
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/detail")
    Flowable<Response<String>> getInformationDetail(@Field("id") String id);

    /**
     * 获取活动列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/recommend/list")
    Flowable<Response<List<HomeRecommendDto>>> getHomeActionRecommend(@Field("pageNo") int pageNo);

    /**
     * 获取活动列表
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/recommend/list")
    Flowable<Response<List<HomeNewRecommendDto>>> getHomeNewActionRecommend(@Field("pageNo") int pageNo);

    /**
     * 获取首页消息
     *
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/notice/list")
    Flowable<Response<List<MessageDto>>> getMessage(@Field("token") String token,
                                                    @Field("pageNo") int pageNo);

    /**
     * 余额支付
     *
     * @param payNumber
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/balance/pay")
    Flowable<Response<String>> payBalance(@Field("token") String token,
                                          @Field("payNumber") String payNumber);

    /**
     * 我的订单  去支付
     *
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/buy/order/not/pay/go/to/pay")
    Flowable<Response<PayOrderDto>> toPayOnMyOrder(@Field("token") String token,
                                                   @Field("orderNo") String orderNo);

    /**
     * 商家 发货
     *
     * @param token
     * @param orderNo
     * @param expressCompany
     * @param courierNumber
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/delivery")
    Flowable<Response<String>> sendGoods(@Field("token") String token,
                                         @Field("orderNo") String orderNo,
                                         @Field("expressCompany") String expressCompany,
                                         @Field("courierNumber") String courierNumber);

    /**
     * 获取订单详情
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/detail")
    Flowable<Response<OrderDto>> getOrderDetails(@Field("token") String token,
                                                 @Field("orderNo") String orderNo);

    /**
     * 确认收货
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/order/buy/receipt")
    Flowable<Response<String>> confirmReceiverGoods(@Field("token") String token,
                                                    @Field("orderNo") String orderNo);

    /**
     * 发表评论
     *
     * @param token
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/comment/buy/order")
    Flowable<Response<String>> evaluateGoods(@Field("token") String token,
                                             @Field("orderNo") String orderNo,
                                             @Field("content") String content);

    /**
     * 获取评论列表
     *
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/list/commet/by/productNo")
    Flowable<Response<List<EvaluateDto>>> getEvaluateList(@Field("token") String token,
                                                          @Field("productNo") String productNo,
                                                          @Field("pageNo") int pageNo);

    /**
     * 获取我的积分
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/integral")
    Flowable<Response<IntegralDto>> getIntegralInfo(@Field("token") String token);

    /**
     * 获取积分明细列表
     *
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/integralDetail")
    Flowable<Response<List<IntegralDetailsDto>>> getIntegralDetailsList(@Field("token") String token,
                                                                        @Field("pageNo") int pageNo);

    /**
     * 扫一扫 二维码
     *
     * @param url
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST()
    Flowable<Response<UserCardDto>> getUserInfoOnQRCode(@Url String url,
                                                        @Field("token") String token);

    /**
     * 申请成为好友
     *
     * @param token
     * @param friendPhone
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/add/friend")
    Flowable<Response<String>> applyBecomeFriend(@Field("token") String token,
                                                 @Field("friendMobilePhone") String friendPhone);

    /**
     * 获取我的人脉列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/list/friend")
    Flowable<Response<List<MyFriendsDto>>> getMyFriends(@Field("token") String token);

    /**
     * 获取分享的内容
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/member/share")
    Flowable<Response<ShareDto>> getShareContent(@Field("token") String token);

    /**
     * 获取我的团队数据
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/list/getMyTeams")
    Flowable<Response<List<MyTeamDto>>> getMyTeam(@Field("token") String token);

    /**
     * 通过用户的id查看名片信息
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/userCard/info/send")
    Flowable<Response<UserCardDto>> getUserCardById(@Field("token") String token,
                                                        @Field("id") String id);


    /**
     * 检测新版本
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/get/app/version")
    Flowable<Response<AppVersionDto>> checkAppVersion(@Field("version") int version);

    /**
     * 支付回调验证
     * @param token
     * @param payNumber
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/show/pay/result")
    Flowable<Response<String>> checkPayResult(@Field("token") String token,
                                                     @Field("payNumber") String payNumber);

    /**
     * 获取首页banner
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/product/normal/bannerList")
    Flowable<Response<List<BannerDto>>> getHomeBanner(@Field("str") String str);

    /**
     * 获取咨询页banner
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/bannerList")
    Flowable<Response<List<InformationDto>>> getInformationBanner(@Field("str") String str);


    /**
     * 获取新版首页 商品分类  一级
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/productTypes/firstList")
    Flowable<Response<List<HomeGoodsTypeDto>>> getHomeGoodsType(@Field("defaule") String str);

    /**
     * 更新用户位置信息
     * @param token
     * @param longitude
     * @param latitude
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/GPS")
    Flowable<Response<String>> updateUserLocation(@Field("token") String token,
                                                  @Field("longitude") String longitude,
                                                  @Field("latitude") String latitude);

    /**
     * 获取附近合伙人信息
     * @param token
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/list/near/partner")
    Flowable<Response<List<NearbyVipDto>>> getNearbyVip(@Field("token") String token,
                                                        @Field("pageNo") int pageNo);

    /**
     * 更新 是否允许其他人查看 名片的状态
     * @param token
     * @param allowStutas
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/update/allowStutas")
    Flowable<Response<String>> updateAllowUserCard(@Field("token") String token,
                                                        @Field("allowStutas") String allowStutas);

    /**
     * 获取俱乐部 的基本数据 logo  名称
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/type")
    Flowable<Response<List<ClubDto>>> getClubInfos(@Field("type") String type);


    /**
     * 创建我的俱乐部
     * @param token
     * @param clubName
     * @param clubLogo
     * @param content
     * @param backgroundImg
     * @param clubDetailList
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/club/addImageDetail")
    Flowable<Response<String>> createMyClub(@Field("token") String token,
                                            @Field("clubName") String clubName,
                                            @Field("clubLogo") String clubLogo,
                                            @Field("content") String content,
                                            @Field("backgroundImg") String backgroundImg,
                                            @Field("clubDetailList") String clubDetailList);

    /**
     * 编辑我的俱乐部
     * @param token
     * @param clubName
     * @param clubLogo
     * @param content
     * @param backgroundImg
     * @param clubDetailList
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/club/editImageDetail")
    Flowable<Response<String>> editMyClub(@Field("token") String token,
                                            @Field("clubName") String clubName,
                                            @Field("clubLogo") String clubLogo,
                                            @Field("content") String content,
                                            @Field("backgroundImg") String backgroundImg,
                                            @Field("clubDetailList") String clubDetailList,
                                          @Field("id") String id);

    /**
     * 获取我的俱乐部基本信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auto/club/detail")
    Flowable<Response<ClubDto>> getMyClubInfoBasic(@Field("token") String token,@Field("clubId") String clubId);


    /**
     * 获取我的俱乐部图文介绍信息
     * @param clubId
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/ImageDetail")
    Flowable<Response<List<ClubDto.ClubDetailBean>>> getMyClubInfoDetails(@Field("clubId") String clubId);

    /**
     * 俱乐部 发布活动
     * @param token
     * @param clubId
     * @param activityPictureUrl
     * @param title
     * @param address
     * @param flow
     * @param holdDate
     * @param guestList
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/club/addOrEditClubActivity")
    Flowable<Response<String>> releaseActionRecent(@Field("token") String token,
                                                   @Field("clubId") String clubId,
                                                      @Field("activityPictureUrl") String activityPictureUrl,
                                                      @Field("title") String title,
                                                      @Field("address") String address,
                                                      @Field("flow") String flow,
                                                      @Field("holdDate") String holdDate,
                                                      @Field("appGuestList") String guestList,
                                                   @Field("id") String actionId);

    /**
     * 获取俱乐部 近期活动列表
     * @param clubId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/activityList")
    Flowable<Response<List<ActionDto>>> getActionRecentList(@Field("clubId") String clubId,
                                                         @Field("pageNo") int pageNo);

    /**
     * 获取为举办 活动详情信息
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/activityDetail/send")
    Flowable<Response<ActionDto>> getActionRecentInfo(@Field("token") String token,
                                                      @Field("id") String id);

    /**
     * 获取活动 的参加人员
     * @param actionId
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/activity/registration/listpage")
    Flowable<Response<List<ActionMemberDto>>> getActionMemberList(@Field("activityId") String actionId,
                                                                  @Field("pageNo") int pageNo);

    /**
     * 俱乐部发布 往期活动
     * @param clubId
     * @param avatarUrl
     * @param title
     * @param dynamicList
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/add")
    Flowable<Response<String>> releaseActionPast(@Field("clubId") String clubId,
                                                 @Field("avatarUrl") String avatarUrl,
                                                 @Field("title") String title,
                                                 @Field("dynamicList") String dynamicList,
                                                 @Field("id") String id);

    /**
     * 获取往期资讯 列表
     * @param clubId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/information/list")
    Flowable<Response<List<ActionPastDto>>> getActionPastList(@Field("clubId") String clubId,
                                                              @Field("pageNo") int pageNo);

    /**
     * 获取往期资讯  详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/information/detail")
    Flowable<Response<ActionPastDto>> getActionPastDetail(@Field("id") String id);

    /**
     * 获取首页  约吗  列表  所有的未举办活动列表
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/activity/list")
    Flowable<Response<List<ActionDto>>> getActionRecentList(@Field("pageNo") int pageNo);

    /**
     * 报名
     * @param token
     * @param activityId
     * @param registrationName
     * @param registrationPhone
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/app/activityRegistration")
    Flowable<Response<String>> applyAction(@Field("token") String token,
                                           @Field("activityId") String activityId,
                                           @Field("registrationName") String registrationName,
                                           @Field("registrationPhone") String registrationPhone);

    /**
     * 获取客服信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/user/admin/service")
    Flowable<Response<ServiceDto>> getServiceInfo(@Field("token") String token);

    /**
     * 获取 近期活动 报名人数
     * @param activityId
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/club/activity/registration/number")
    Flowable<Response<ActionMemberNumDto>> getActionMemberNum(@Field("activityId") String activityId);

    /**
     * 发布捡漏
     * @param token
     * @param ruleTitle
     * @param ruleContent
     * @param rulePictureUrl
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/add/rule")
    Flowable<Response<String>> releaseForum(@Field("token") String token,
                                            @Field("ruleTitle") String ruleTitle,
                                            @Field("ruleContent") String ruleContent,
                                            @Field("rulePictureUrl") String rulePictureUrl);

    /**
     * 获取 捡漏列表
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/rule/list")
    Flowable<Response<List<ForumDto>>> getForumList(@Field("token") String token,
                                                    @Field("pageNo") int pageNo);

    /**
     * 捡漏 帖子点赞
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/rule/praise")
    Flowable<Response<String>> likeForum(@Field("token") String token,
                                         @Field("id") String id);

    /**
     * 给 捡漏 帖子 发表评论
     * @param token
     * @param id
     * @param commentContent
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/add/rule/comment")
    Flowable<Response<String>> addForumComment(@Field("token") String token,
                                               @Field("id") String id,
                                               @Field("ruleCommentContent") String commentContent);

    /**
     * 获取更多的  捡漏帖子 评价
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/rule/comment/list")
    Flowable<Response<List<ForumDto.ForumCommentDto>>> getMoreCommentList(@Field("id") String id);

    /**
     * 获取 我的捡漏 信息  评论数  获赞数  发帖数
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/my/rule/statistics")
    Flowable<Response<ForumInfoDto>> getMyForumInfos(@Field("token") String token);

    /**
     * 获取 我的捡漏专区  具体的  帖子列表
     * @param token
     * @param type
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/auth/my/rule/details")
    Flowable<Response<List<ForumDto>>> getMyForumList(@Field("token") String token,
                                                      @Field("type") String type,
                                                      @Field("pageNo") int pageNo);

    /**
     * 上传app crash 的情况到服务器
     * @param appVersion
     * @param osVersion
     * @param vendor
     * @param model
     * @param cpuabi
     * @param question
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH + "/appquestion/save")
    Flowable<Response<String>> uploadAppCrashQuestion(@Field("appVersion") String appVersion,
                                                      @Field("osVersion") String osVersion,
                                                      @Field("vendor") String vendor,
                                                              @Field("model") String model,
                                                              @Field("cpuabi") String cpuabi,
                                                              @Field("question") String question);
}
