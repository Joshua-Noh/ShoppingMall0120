package com.shop.ShoppingMall_TeamPrj.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;


@Controller
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH = "C:\\shoppingMall\\list_image";
	
	@RequestMapping("/download")
	protected void download(@RequestParam("fileName") String fileName,
		                 	@RequestParam("goods_id") String goods_id,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath=CURR_IMAGE_REPO_PATH+"\\"+goods_id+"\\"+fileName;
		File image=new File(filePath);

		response.setHeader("Cache-Control","no-cache");
		response.addHeader("Content-disposition", "attachment; fileName="+fileName);
		FileInputStream in=new FileInputStream(image); 
		byte[] buffer=new byte[1024*8];
		while(true){
			int count=in.read(buffer); //버퍼에 읽어들인 문자개수
			if(count==-1)  //버퍼의 마지막에 도달했는지 체크
				break;
			out.write(buffer,0,count);
		}
		in.close();
		out.close();
	}
	
	
	@RequestMapping("/common/thumbnails.do")
	protected void thumbnails(@RequestParam("fileName") String fileName,
	                          @RequestParam("product_id") String product_id,
	                          HttpServletResponse response) throws Exception {
	    response.setContentType("image/png");
	    OutputStream out = response.getOutputStream();
	    String filePath = CURR_IMAGE_REPO_PATH + "\\" + product_id + "\\" + fileName;
	    File image = new File(filePath);
	    
	    if (image.exists()) { 
	        Thumbnails.of(image)
	                   .size(121, 154)
	                   .outputFormat("png")
	                   .toOutputStream(out);
	    }
	    out.close();
	}

	@RequestMapping("/common/largeImage.do")
	protected void largeImage(@RequestParam("fileName") String fileName,
	                          @RequestParam("product_id") String product_id,
	                          HttpServletResponse response) throws Exception {
	    // 메인에 표시할 큰 이미지의 컨텐츠 타입을 설정 (예: JPEG)
	    response.setContentType("image/jpeg");
	    OutputStream out = response.getOutputStream();
	    
	    // 이미지 파일 경로 구성 (예시 경로)
	    String filePath = CURR_IMAGE_REPO_PATH + "\\" + product_id + "\\" + fileName;
	    File image = new File(filePath);
	    
	    if (image.exists()) {
	        // Thumbnailator를 사용하여 큰 이미지 (예: 800x600)로 리사이즈하여 출력
	        Thumbnails.of(image)
	                   .size(800, 600)  // 필요에 따라 원하는 사이즈로 조정
	                   .outputFormat("jpeg")
	                   .toOutputStream(out);
	    } else {
	        // 파일이 없으면 404 에러 전송
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "이미지를 찾을 수 없습니다.");
	    }
	    out.close();
	}

}
