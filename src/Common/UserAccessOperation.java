package Common;

public class UserAccessOperation extends SqlAble{
	public Integer user_type; // 1	<-------------->	BD����
	 /* user_type = 2	<-------------->	�ܼ�
	 /* user_type = 3	<-------------->	�ܾ���
	 /* user_type = 4	<-------------->	��ά��Ա
	  */
	public Integer personal_information;	//	������Ϣ
	public Integer change_password;	//	�޸�����
	public Integer create_customer;	//	�����ͻ�
	public Integer modify_customer;	//	�޸Ŀͻ�
	public Integer profile_customer;	//	�鿴�ͻ�
	public Integer delete_customer;	//	ɾ���ͻ�
	public Integer charge_customer;	//	�ͻ���ֵ
	public Integer charge_log;	//		��ֵ��¼�鿴
	public Integer service_list;	//	�����б�
	public Integer service_setup_price;	//	���񶨼�
	public Integer service_price_log;	//	���۲�ѯ
	public Integer service_analysis;	//	����ͳ��
	public Integer service_details;	//	ʹ������
}
