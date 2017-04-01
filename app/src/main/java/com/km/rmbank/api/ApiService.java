package com.km.rmbank.api;

import com.km.rmbank.dto.DefaultDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.Response;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.utils.SecretConstant;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by kamangkeji on 17/2/10.
 */

public interface ApiService {

    /**
     * 登录
     * @param mobilePhone
     * @param loginPwd
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/user/login")
    Observable<Response<UserDto>> login(@Field("mobilePhone") String mobilePhone,
                                        @Field("loginPwd") String loginPwd);

    /**
     * 注册
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/user/register")
    Observable<Response<DefaultDto>> userRegister(@Field("mobilePhone") String mobilePhone,
                                                  @Field("loginPwd") String password,
                                                  @Field("smsCode") String smsCode);

    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/sms/send/code")
    Observable<Response<DefaultDto>> getPhoneCode(@Field("mobilePhone") String mobilePhone);

    /**
     * 忘记密码
     * @param mobilePhone
     * @param password
     * @param smsCode
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/user/forgetLoginPwd")
    Observable<Response<DefaultDto>> forgetLoginPwd(@Field("mobilePhone") String mobilePhone,
                                                    @Field("pwd") String password,
                                                    @Field("smsCode") String smsCode);


    /**
     * 个人信息
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/info")
    Observable<Response<UserInfoDto>> getUserInfo(@Field("token") String token);

    /**
     * 修改个人信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/update/info")
    Observable<Response<String>> updateUserInfo(@Field("token") String token,
                                                              @Field("nickName") String nickName,
                                                              @Field("portraitUrl") String portraitUrl,
                                                              @Field("birthday") String birthday);

    /**
     * 生成个人名片
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
    @POST(SecretConstant.API_HOST_PATH+"/auth/userCard/update/info")
    Observable<Response<UserCardDto>> createUserCard(@Field("token") String token,
                                                     @Field("name") String name,
                                                     @Field("mobilePhone") String mobilePhone,
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
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/userCard/info")
    Observable<Response<UserCardDto>> getUserCard(@Field("token") String token);

    /**
     * 获取行业数据
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/list/industry")
    Observable<Response<List<IndustryDto>>> getIndustryList(@Field("token") String token);




    /**
     * 上传图片
     * @param params
     * @return
     */
    @Multipart
    @POST(SecretConstant.API_HOST_PATH+"/file/up")
    Observable<Response<String>> imageUpload(@PartMap Map<String, RequestBody> params);

    /**
     * 查询账户余额
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/my/account")
    Observable<Response<UserBalanceDto>> getUserAccountBalance(@Field("token") String token);

    /**
     * 查询账户明细
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/account/user/stream/list")
    Observable<Response<List<UserAccountDetailDto>>> getUserAccountDetail(@Field("token") String token,
                                                                    @Field("pageNo") int pageNo);


    /**
     * 新增提现账户
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/withdraw/add/account")
    Observable<Response<String>> createWithDrawAccount(@Field("token") String token,
                                                       @Field("name") String name,
                                                       @Field("withdrawPhone") String withdrawPhone,
                                                       @Field("typeName") String typeName,
                                                       @Field("withdrawNumber") String withdrawNumber);

    /**
     * 编辑提现账户
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/withdraw/update/account")
    Observable<Response<String>> updateWithDrawAccount(@Field("token") String token,
                                                       @Field("name") String name,
                                                       @Field("withdrawPhone") String withdrawPhone,
                                                       @Field("typeName") String typeName,
                                                       @Field("withdrawNumber") String withdrawNumber);

    /**
     * 获取提现账户列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/auth/user/withdraw/list/account")
    Observable<Response<List<WithDrawAccountDto>>> getWithDrawAccount(@Field("token") String token);


    /**
     * 获取商品列表  商城
     * @param pageNo
     * @returnr
     */
    @FormUrlEncoded
    @POST(SecretConstant.API_HOST_PATH+"/product/normal/list")
    Observable<Response<List<GoodsDto>>> getGoodsListOfShopping(@Field("pageNo") int pageNo);

}
