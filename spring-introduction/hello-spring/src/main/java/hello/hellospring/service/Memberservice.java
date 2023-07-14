package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class Memberservice {

    private final MemberRepository memberRepository; // final은 한번만 사용할 수 있는 엔티티(데이터베이스 테이블과 매핑되는 자바 클래스) 정의할 때 사용

    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    public Memberservice(MemberRepository memberRepository) {  // 직접생성하는것이 아닌 넣어주는 식으로
        this.memberRepository = memberRepository;
    } // 회원 리포지토리 코드가 회원 서비스 코드를 DI 가능하게 변경한다.

    /*
    * 회원가입
     */
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원 x

        // Optional 안에 객체를 감쌈 null 일경우 Optional로 감쌈 꺼내고싶으면 get()쓰면됨
        // Optional로 바로 반환하는게 별로 좋지 않음
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        })
        validateDuplicateMember(member);  // 중복회원검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) { // 위 중복회원 검증 메소드
        memberRepository.findByName(member.getName())  //결과가 바로 Optional 임
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");  // try catch 말고 이런식으로 예외처리가 가능
                        });
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}