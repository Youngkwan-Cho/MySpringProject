package com.example.crud;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;

public class MemberController {
    MemberDAO memberDAO;

    @RequestMapping(value="/board/list", method = RequestMethod.GET)
    public String boardlist(Model model) throws SQLException {
        model.addAttribute("list", memberDAO.getList());
        return "board/posts";
    }

    @RequestMapping(value="/board/add", method = RequestMethod.GET)
    public String addPost(){
        return "board/addform";
    }

    @RequestMapping(value="/board/addok", method = RequestMethod.GET)
    public String addPostOK(MemberVO vo) {
        int i = MemberDAO.insertMember(vo);
        if (i==0) System.out.println("회원 추가 실패 ");
        else System.out.println("회원 추가 성공!!! ");
        return "redirect:list";
    }

    @RequestMapping(value="/board/edit/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") Integer id, Model model) {
        MemberVO memberVO = memberDAO.getMember(id);
        model.addAttribute("memberVO", memberVO);
        return "board/editform";
    }

    @RequestMapping(value="/board/editok", method = RequestMethod.GET)
    public String editPostOK(MemberVO vo) {
        int i = MemberDAO.updateMember(vo);
        if (i==0) System.out.println("회원 수정 실패 ");
        else System.out.println("회원 수정 성공!!! ");
        return "redirect:list";
    }

    @RequestMapping(value="/board/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") Integer id, Model model) {
        int i = memberDAO.deleteMember(id);
        if (i==0) System.out.println("회원 삭제 실패 ");
        else System.out.println("회원 삭제 성공!!! ");
        return "redirect:list";
    }
 }
