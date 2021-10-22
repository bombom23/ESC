package com.esc.khweb.controller;

import com.esc.khweb.service.BoardService;
import com.esc.khweb.controller.request.BoardRequest;
import com.esc.khweb.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@Slf4j
public class BoardController {

    @Autowired
    BoardService service;

    @PostMapping("/uploadImg/{name}/{randomNumToString}")
    @ResponseBody
    public String requestUploadFile(@RequestParam("fileList") List<MultipartFile> fileList , @PathVariable("name") String name ,@PathVariable("randomNumToString") String randomNumToString )
    {

        System.out.println("name = " +name );
        log.info("requestUploadFile(): " + fileList);

        try {
            // 결국 저장되는 위치가 images/사진파일명.확장자
            // images/아이디/사진파일명.확장자

            for (MultipartFile multipartFile : fileList) {
                log.info("requestUploadFile(): Make File");
                FileOutputStream writer = new FileOutputStream("C:\\proj\\ESC\\frontend\\src\\assets\\게시판/"+randomNumToString+name+"의"+multipartFile.getOriginalFilename());
                writer.write(multipartFile.getBytes());
                writer.close();


            }


        } catch (Exception e) {
            return "Upload Fail!!!";
        }

        log.info("requestUploadFile(): Success!!!");

        return "success!";
    }
    @PostMapping("/boardRegister")
    public ResponseEntity<Void> boardRegister (@Validated @RequestBody BoardRequest boardRequest) throws  Exception{

        service.boardRegister(boardRequest);

            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getBoardList")
    public ResponseEntity<List<Board>> getBoardList () throws  Exception {

        List<Board> list = service.getBoardList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/getBoard/{boardNo}")
    public  ResponseEntity<Board> getBoard (@PathVariable ("boardNo")Long boardNo) throws  Exception {

        Optional<Board> board = service.getBoard(boardNo);
        Board board1 = board.get();


        return new ResponseEntity<>(board1, HttpStatus.OK);
    }

    @PostMapping("/viewcount/{boardNo}")
    public ResponseEntity<Void>viewcount (@PathVariable ("boardNo") Long boardNo) throws  Exception {

        service.viewcount(boardNo);

        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/goodCount/{boardNo}")
    public ResponseEntity<Void> goodCount (@PathVariable ("boardNo") Long boardNo) throws  Exception {

        service.goodCount(boardNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/badCount/{boardNo}")
    public ResponseEntity<Void> badCount (@PathVariable ("boardNo") Long boardNo) throws  Exception {

        service.badCount(boardNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/report/{boardNo}")
    public ResponseEntity<Void> report (@PathVariable ("boardNo")Long boardNo, @Validated @RequestBody BoardRequest boardRequest) throws  Exception {
        String reportWord = boardRequest.getReportWord();
        service.report(boardNo,reportWord);

        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/boardModify/{boardNo}")
    public ResponseEntity<Void> boardModify(@PathVariable("boardNo")Long boardNo, @Validated @RequestBody BoardRequest boardRequest) throws  Exception {

        service.boardModify(boardRequest,boardNo);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/DeleteBoard/{boardNo}")
    public ResponseEntity<Void> DeleteBoard (@PathVariable("boardNo")Long boardNo) throws  Exception {

        service.DeleteBoard(boardNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/getTargetList/{target}")
    public  ResponseEntity<List<Board>> getTargetList (@PathVariable ("target") String target) throws  Exception {

        List<Board> list = service.getTargetList(target);
        //거꾸로
        Collections.reverse(list);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/getSearchList/{search}")
    public ResponseEntity<List<Board>> getSearchList (@PathVariable("search") String search) throws  Exception {

        List<Board> list  = service.getSearchList(search);
        Collections.reverse(list);

        return  new ResponseEntity<>(list,HttpStatus.OK);

    }
 }
