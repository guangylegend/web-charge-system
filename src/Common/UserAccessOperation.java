package Common;

public class UserAccessOperation extends SqlAble{
	public Integer user_type; // 1	<-------------->	BD经理
	 /* user_type = 2	<-------------->	总监
	 /* user_type = 3	<-------------->	总经理
	 /* user_type = 4	<-------------->	运维人员
	  */
	public Integer personal_information;	//	个人信息
	public Integer change_password;	//	修改密码
	public Integer create_customer;	//	新增客户
	public Integer modify_customer;	//	修改客户
	public Integer profile_customer;	//	查看客户
	public Integer delete_customer;	//	删除客户
	public Integer charge_customer;	//	客户充值
	public Integer charge_log;	//		充值记录查看
	public Integer service_list;	//	服务列表
	public Integer service_setup_price;	//	服务定价
	public Integer service_price_log;	//	定价查询
	public Integer service_analysis;	//	服务统计
	public Integer service_details;	//	使用详情
}
