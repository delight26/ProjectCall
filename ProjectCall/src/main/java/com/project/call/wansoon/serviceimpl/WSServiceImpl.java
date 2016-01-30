package com.project.call.wansoon.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.call.domain.FreeBoard;
import com.project.call.wansoon.dao.WSDao;
import com.project.call.wansoon.service.WSService;

@Service
public class WSServiceImpl implements WSService {

	@Autowired
	private WSDao jBDao;
	
	public void setjBDao(WSDao jBDao) {
		this.jBDao = jBDao;
	}

	@Override
	public List<FreeBoard> getFreeBoardAll() {
		
		return jBDao.getFreeBoardAll();
	
	}

	@Override
	public FreeBoard getFreeBoard(int frbNo) {
		
		return jBDao.getFreeBoard(frbNo);
	}

	@Override
	public List<FreeBoard> insertBoard(FreeBoard freeboard) {
		
		return jBDao.insertBoard(freeboard);
	}

	@Override
	public void addWrite(MultipartHttpServletRequest request, String filePath) throws IOException {
	MultipartFile multipartFile = request.getFile("image");
		
		// 업로드된 파일 데이터가 존재하면
		if(!multipartFile.isEmpty()) {
			
			File file = new File(filePath, multipartFile.getOriginalFilename());
			
			// 업로드한 파일 데이터를 지정한 파일로 저장한다.
			multipartFile.transferTo(file);
			
			// 파일 업로드가 완료되면 ProductDao를 이용해 상품 정보를 DB에 저장한다.
			FreeBoard frb = new FreeBoard();
			
			frb.setFrbNo(Integer.parseInt(request.getParameter("no")));
			frb.setFrbTitle(request.getParameter("frbTitle"));
			frb.setFrbPass(request.getParameter("frbPass"));
			frb.setFrbContent(request.getParameter("frbContent"));
			frb.setPhoto1(multipartFile.getOriginalFilename());
			frb.setFrbWriteDate(new Timestamp(System.currentTimeMillis()));
			frb.setFrbHit(Integer.parseInt(request.getParameter("frbHit")));
			frb.setFrbArea(request.getParameter("frbArea"));
			frb.setFrbEmail(request.getParameter("frbEmail"));
			frb.setFrbWriter(request.getParameter("frbWriter"));
			
			jBDao.addWrite(frb);
		
		}
	}


	
	
	}
