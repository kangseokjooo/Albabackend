package com.jobstore.jobstore.service.work;

import com.jobstore.jobstore.dto.WorkDto;
import com.jobstore.jobstore.dto.request.work.WorkCreateDto;
import com.jobstore.jobstore.dto.request.work.WorkUpdateDto;
import com.jobstore.jobstore.dto.response.work.WorkDetailDto;
import com.jobstore.jobstore.dto.response.work.WorkPagenationDto;
import com.jobstore.jobstore.entity.Comment;
import com.jobstore.jobstore.entity.Contents;
import com.jobstore.jobstore.entity.Member;
import com.jobstore.jobstore.entity.Work;
import com.jobstore.jobstore.repository.MemberRepository;
import com.jobstore.jobstore.repository.work.CommentsRepository;
import com.jobstore.jobstore.repository.work.ContentRepository;
import com.jobstore.jobstore.repository.work.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private MemberRepository memberRepository;

    // 페이지 네이션
    public WorkPagenationDto searchPagenation (long storeid, String search, Integer page){

        Integer size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "workid");

        String SearchLike = "%" + search + "%";
        Page<Work> work =  workRepository.findByStoreidAndTitleLike(storeid, SearchLike, pageRequest);
        //work를 DTO로 변환
        Page<WorkDto> toMap = work.map(m -> new WorkDto(m.getWorkid(), m.getTitle(), m.getDate()));

        WorkPagenationDto dto = new WorkPagenationDto(
                toMap.getContent(),
                toMap.getContent().size(),
                toMap.getNumber(),
                toMap.getTotalPages(),
                toMap.hasNext()
        );
        return dto;

    }


    // 페이지 네이션
    public WorkPagenationDto findPagenation (long storeid, Integer page){

        Integer size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "workid");
        Page<Work> work =  workRepository.findByStoreid(storeid, pageRequest);

        //work를 DTO로 변환
        Page<WorkDto> toMap = work.map(m -> new WorkDto(m.getWorkid(), m.getTitle(), m.getDate()));

        WorkPagenationDto dto = new WorkPagenationDto(
                toMap.getContent(),
                toMap.getContent().size(),
                toMap.getNumber(),
                toMap.getTotalPages(),
                toMap.hasNext()
        );
        return dto;

    }

    public WorkDetailDto boardDetail (long workid) {

        Optional<Work> work = workRepository.findByWorkid(workid);

        if (work.isPresent()) {

            WorkDetailDto workDetailDto = new WorkDetailDto();
            workDetailDto.setWorkid(work.get().getWorkid());
            workDetailDto.setTitle(work.get().getTitle());
            workDetailDto.setDate(work.get().getDate());
            workDetailDto.setContents(contentRepository.findByWorkWorkid(workid, Sort.by(Sort.Direction.DESC, "contentsid")));
            workDetailDto.setComment(commentsRepository.findByWorkWorkid(workid, Sort.by(Sort.Direction.DESC, "commentid")));

            return workDetailDto;
        }
        return null;
    }


    public HashMap createWorkBoard (WorkCreateDto workCreateDto) {

        Optional<Member> member = memberRepository.findByMemberid(workCreateDto.getMemberid());
        HashMap result = new HashMap();


        if (!member.isPresent()) {
            result.put("message", "noMember");
            return result;
        }
        if (!member.get().getRole().equals("ADMIN")) {
            result.put("message", "noAuth");
            return result;
        }

        Work work = new Work();
        work.setStoreid(workCreateDto.getStoreid());
        work.setTitle(workCreateDto.getTitle());
        work.setDate(workCreateDto.getDate());
        workRepository.save(work);

        result.put("message", "success");
        result.put("workid", work.getWorkid());
        return result;
    }

    public String updateWorkBoard (WorkUpdateDto workUpdateDto) {

        Optional<Work> work = workRepository.findByWorkid(workUpdateDto.getWorkid());
        if (!work.isPresent()) {
            return "nodata";
        }
        work.get().setTitle(workUpdateDto.getTitle());
        workRepository.save(work.get());
        return  "success";
    }

    public int deleteWorkBoard (int id) {

        Optional<Work> work = workRepository.findByWorkid(id);
        if (!work.isPresent()) {
            return 3;
        }

        work.get().getContents().removeAll(work.get().getContents());
        work.get().getComments().removeAll(work.get().getComments());

        workRepository.save(work.get());

        int result = workRepository.deletebyWorkid(id);

        return result;
    }
}
