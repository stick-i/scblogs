package cn.sticki.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class ResponseUtils {

	@Value("${spring.jackson.date-format}")
	private String dateFormat;

	public void objectToJson(HttpServletResponse response, Object object) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		ObjectMapper mapper = new ObjectMapper();
		// todo 注释上说这是线程不安全的，可能需要修改
		mapper.setDateFormat(new SimpleDateFormat(dateFormat));
		mapper.writeValue(response.getWriter(), object);
	}

}
