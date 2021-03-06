package com.skysea.alipay;

public class PartnerConfig {

	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088101773878446";

	// 商户收款的支付宝账号
	public static final String SELLER = "2088101773878446";

	// 商户（RSA）私钥
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMXDzG0pg26fnOdUlJixRuw0aa/DYRBy5NzjXyX7WbKKb+4/l+qxUiWI3EItujtoEa9aYIwPuqIsJOPwqERIJi2JIy536lcWXRa/HftR8kbwKb4U9oXJknXd/SAAOj81k6ThFMvdv+Zu/5xlWX7IxBXGtFBYC/3y6Ee9zDpuL0cHAgMBAAECgYEAky19RLxBYSGOPqa2k9BS7NSw1qp74SbwZecc14JwpavbUx6mq3Xdoatx6Bpp3246wxpqgbHu/mQF7FEcmPxs0+SWBxB/ZYIvTa/OrALMtXTG+Lsz1Rnen8RFdln++SvNVLlTmg+uriJOrb80RqykQBLssmelKsoO9bf2FrmjzRECQQD0KQ2WIMcRwptPHiPunARTTq9m5yY6xAb/1xIATmPRlROXHg6lzD9SdSE1z4wZdb6xlRL2zFSp7aseEb3aZDCfAkEAz1rNgy+KpTkfbOPkddiAssMP+F/7mzyrn4zZai0qwPQP2ZR2tbmaGYojxc/iOu9lYRw1oCcRfmGxvCw5gm7ImQJBAJmgSyAvN5KBNF+vw0Qbfs08MTk6L9/B//VySa1PpZb1igIHs4e24BDuChtln+1VHMt31RrcrljNZmHhH+gOKlkCQQCWIb2pHVP6Wwio07xHoxFT3S4D/KL0/BGbPOTyHm/VMyECvZ7R5udvVHigMeswFsNFc6JUKdVdtyh0AWVGTiYJAkA7l+tVqQFj4tfkSPvn3REs6MVVh3hRCVG6gbmDQI9LuE1QmpJ8bETeST4tEtTdmG0p3FbICKxV+il4OLKC3PRF";

	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl+PKDx3rvn506OJwWHowwA2P7L4bbKLGegCLwY4fL1L7JlCn+LanCoqNTbHqugQ+U4wK/EPm6tA6AzRcMFYHnrZmb/Ycnrc2nvs3lDNKo8IDj6O+0bmMiAIN+f809lbqi/aJhPwvFQpUfXWuR9YW1a3LOMy2AQV9AbPJrOp+SGwIDAQAB";

	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin_msp.apk";

}
