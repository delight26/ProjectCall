package com.project.call.junbum.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.call.domain.Member;
import com.project.call.domain.PointProduct;
import com.project.call.junbum.dao.JBDao;

@Service
public class JBServiceImpl implements JBService {

	@Autowired
	private JBDao jBDao;
	private List<PointProduct> pList = new ArrayList<PointProduct>();

	public void setjBDao(JBDao jBDao) {
		this.jBDao = jBDao;
	}

	@Override
	public Boolean loginResult(HttpServletRequest request, HttpSession session) {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");

		Member m = jBDao.getloginResult(email);

		if (m.getPass().equals(pass)) {
			session.setAttribute("loginUser", m);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void getproductList(HttpServletRequest request) {
		List<PointProduct> pList = jBDao.getproductList();

		request.setAttribute("pList", pList);
	}

	@Override
	public void addProduct(MultipartHttpServletRequest request, String path) throws IOException {
		MultipartFile multipartFile = request.getFile("image");

		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);

			PointProduct p = new PointProduct();

			p.setpName(request.getParameter("pname"));
			p.setpPrice(Integer.valueOf(request.getParameter("price")));
			p.setpAmount(Integer.valueOf(request.getParameter("amount")));
			p.setpImage(multipartFile.getOriginalFilename());

			jBDao.addProduct(p);
		}
	}

	@Override
	public void productContent(HttpServletRequest request) {
		int pNo = Integer.valueOf(request.getParameter("pNo"));

		PointProduct prod = jBDao.productContent(pNo);

		request.setAttribute("prod", prod);
	}

	@Override
	public void productUpdate(HttpServletRequest request) {
		int pProductCode = Integer.valueOf(request.getParameter("pProductCode"));

		PointProduct prod = jBDao.productContent(pProductCode);

		request.setAttribute("prod", prod);
	}

	@Override
	public void productUpdateResult(MultipartHttpServletRequest request, String path) throws IOException {
		MultipartFile multipartFile = request.getFile("image");

		if (!multipartFile.isEmpty()) {
			File file = new File(path, multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);

			PointProduct p = new PointProduct();
			p.setpProductCode(Integer.valueOf(request.getParameter("pProductCode")));
			p.setpName(request.getParameter("pname"));
			p.setpPrice(Integer.valueOf(request.getParameter("price")));
			p.setpAmount(Integer.valueOf(request.getParameter("amount")));
			p.setpImage(multipartFile.getOriginalFilename());

			jBDao.updateProduct(p);
		}
	}

	@Override
	public void productDelete(HttpServletRequest request) {
		int pProdcutCode = Integer.valueOf(request.getParameter("pProductCode"));

		jBDao.productDelete(pProdcutCode);
	}

	@Override
	public void addCart(HttpServletRequest request, HttpSession session) {
		int pNo = Integer.valueOf(request.getParameter("pNo"));
		int quentity = Integer.valueOf(request.getParameter("quentity"));

		PointProduct prod = jBDao.productContent(pNo);

		int check = 0;
		prod.setpQuantity(quentity);
		if (pList.size() == 0) {
			pList.add(prod);
		} else {
			for (int i = 0; i < pList.size(); i++) {
				if (prod.getpProductCode() == pList.get(i).getpProductCode()) {
					check += 1;
					pList.get(i).setpQuantity(quentity);
				}
			}
			if (check == 0) {
				pList.add(prod);
			}
		}
	}

	@Override
	public void getCart(HttpSession session) {
		session.setAttribute("pList", pList);
	}

	@Override
	public void buyProduct(HttpServletRequest request) {
		String[] pCodeList = request.getParameterValues("checkbox");
		ArrayList<PointProduct> pList = new ArrayList<PointProduct>();
		for (int i = 0; i < pCodeList.length; i++) {
			int pNo = Integer.valueOf(pCodeList[i]);
			PointProduct p = jBDao.productContent(pNo);
			pList.add(p);
		}
		request.setAttribute("pList", pList);
	}
}