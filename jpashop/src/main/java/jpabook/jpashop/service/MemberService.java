package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * Join Member
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> memberList = memberRepository.findByName(member.getName());
        if(!memberList.isEmpty()) {
            throw new IllegalStateException("This member already exists.");
        }
    }

    /**
     * find all members
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * find single member by memberId
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
