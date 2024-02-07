// 11장 p.34-35 부분 p.40,41로 연계
package com.example.demo.openAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class OpenAPITest2 {

	String serviceKey ="7cFa7FUA5CR6%2FxN2Gf%2BFLQLg06CXkTF6uKD0A8GkeLISxqHK%2BZlqdEhYi6zXaka63O1mCXd5ldiPDTfx4UFloA%3D%3D";// www.data.go.kr->마이페이지
	String dataType ="JSON";
	String code ="11B20201"; // API 가이드파일 中 엑셀페이지에서 지점목록에서 가져옴
	
	// p.40 => @Test없앰
	public String getWeather() throws IOException { // p.40 => 리턴타임 void->String
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")); /*11A00101(백령도), 11B10101 (서울), 11B20201(인천) 등... 별첨 엑셀자료 참조(‘육상’ 구분 값 참고)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
//        System.out.println();
//        System.out.println(sb.toString());
//        System.out.println();        
        
        return sb.toString(); // p.40 리턴타입을 문자열로 변경하고 API 결과를 반환한다. 이건 ResponseResult.java의 클래스에 @ToString전부 달아서 가능
	}
	
	
	// p.41 json문자열을 클래스로 변환하는 메서드 만들기
	@Test
	public void jsonToDto() throws IOException {
		//json 문자열을 클래스로 변환해주는 매퍼 클래스(ObjectMapper) 설정
		ObjectMapper mapper = new ObjectMapper();
		
		//분석할 수 없는 json구문을 무시하는 옵션 설정 (이거 안넣으면 분석못한 json값 있을 시에 실패되버림)
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
									// ㄴ key								ㄴ value		
		//날씨 데이터 가져오기
		String weather = getWeather(); // getWeather() 위에 있는 내부메서드다
		
		//JSON 문자열을 클래스로 변환
		Root root = null;		
		root = mapper.readValue(weather, Root.class); // 매퍼를 이용하여 날씨 데이터를 자바 객체로 변환한다.(파싱)
								// ㄴ json값   ㄴ설계도(ResponseResult 클래스의 최상위 클래스 넣기)
		//결과 코드만 추출
		System.out.println(root.response.header.resultCode);
		//결과 메세지만 추출
		System.out.println(root.response.header.resultMsg);		
		
	}
}