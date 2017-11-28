package pinyin4j;

import org.apache.commons.lang3.StringUtils;

import com.itheima.bos.utils.PinYin4jUtils;

public class Test {
	@org.junit.Test
	public void fun1() {
		String province = "广东省";
		String city = "深圳市";
		String district = "龙岗区";
		
		province = province.substring(0, province.length()-1);
		city = city.substring(0, city.length()-1);
		district = district.substring(0, district.length()-1);
		
		String info = province + city + district;
		
		String[] strings = PinYin4jUtils.getHeadByString(info);
		String shortcode = StringUtils.join(strings);
		System.out.println(shortcode);
		String citycode =	PinYin4jUtils.hanziToPinyin(city,"");
		System.out.println(citycode);
	}
}
