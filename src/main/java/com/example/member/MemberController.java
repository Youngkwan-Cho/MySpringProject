package com.example.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.sql.SQLException;

@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberDAO;
    @RequestMapping(value="/")
    public String home() {
        return "board/index";
    }

    @RequestMapping(value="/board/list", method = RequestMethod.GET)
    public String boardlist(Model model) throws SQLException {
        model.addAttribute("list", memberDAO.getList());
        return "board/list";
    }
    @RequestMapping(value="/board/add", method = RequestMethod.GET)
    public String addPost(){
        return "board/addform";
    }

    @RequestMapping(value="/board/addok", method = RequestMethod.POST)
    public String addPostOK(MemberVO vo) {
        int i = memberDAO.insertMember(vo);
        if (i==0) System.out.println("회원 추가 실패 ");
        else System.out.println("회원 추가 성공!!! ");
        return "redirect:./list";
    }

    @RequestMapping(value="/board/edit/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model) {
        MemberVO memberVO =memberDAO.getMember(id);
        model.addAttribute("u", memberVO);
        return "board/editform";
    }

    @RequestMapping(value="/board/editok", method = RequestMethod.POST)
    public String editPostOK(MemberVO vo) {
        int i = memberDAO.updateMember(vo);
        if (i==0) System.out.println("회원 수정 실패 ");
        else System.out.println("회원 수정 성공!!! ");
        return "redirect:./list";
    }

    @RequestMapping(value="/board/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") int id) {
        int i = memberDAO.deleteMember(id);
        if (i==0) System.out.println("회원 삭제 실패 ");
        else System.out.println("회원 삭제 성공!!! ");
        return "redirect:../list";
    }
 }
