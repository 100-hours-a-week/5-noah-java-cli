import Facade.CafeFacade;
import Facade.CafeFacadeImpl;
import repository.BeanRepository;
import repository.EmployeeRepository;
import utils.Utils;

public class Main {

    public static void main(String[] args) {
        String title = """
                ##    ##   ####      ##### ###     \s
                 ##   #   # # ##    ## ##   ## ##  \s
                 ###  #  ## #  ##  ##  ##   ### ## \s
                 #### #  ## #  ##  ######  ###  ## \s
                 #  ###  ##    ##  ##  ##   ##  #  \s
                 #   ##   ##  ##   ##  ##  ##   #  \s
                ##    ##   ####   ##    ## ##  ####\s
                """;
        String[] description = {title,
                "카카오 클라우드 스쿨 in JEJU 6주차 과제 CLI 프로그램 만들기",
                "                                     9팀: noah.jo",
                "프로그램",
                " - 카페 시뮬레이션 게임",
                "프로그램 목표",
                " - 유사 MVC, Facade, Builder 패턴 사용",
                " - 게임성 유지",
                "프로그램 설명",
                " 노아는 방주를 만들기 위해 목재가 필요합니다.",
                " 커피를 좋아하는 노아는 커피를 팔아 목재를 구하려 합니다.",
                " 바쁜 노아를 위해 카페를 관리해 주세요!",
                "  - 총 5일 동안 진행됩니다. 100원을 모으세요!",
                "  - 직원은 하루 1번 고용할 수 있습니다.",
                "  - 직원은 지원서에 일급을 제시합니다. 이를 통해 고용 여부를 판단하세요!",
                "  - 직원은 해고할 수 있습니다.",
                "  - 날마다 1명의 직원은 1번의 행동을 할 수 있습니다.",
                "  - 가게를 마감하면 직원들에게 일급을 지급합니다, 지불할 수 없다면 가게를 떠나니 주의하세요!",
                "  - 원두의 가격은 2원입니다.",
                "  - 원두는 소비기한이 있습니다, 소비기한을 넘기면 원두는 자동 폐기됩니다, 발주에 주의하세요.",
                "  - 커피는 원두를 1개 소모하며, 커피 종류, 크기, 온도를 정할 수 있습니다, 간단한 벨런스를 위해 옵션 선택에 따른 이윤은 고정입니다.",
                "  - 커피를 주문한 손님은 10% 확률로 돈을 내지 않고 도망갑니다.",
                "  - 목표 금액 달성, 해고 직원 유무, 떠난 직원 유무, 버려진 원두 유무에 따라 엔딩 글귀가 달라집니다.",
                "기타",
                " 벤치마킹 게임",
                "  - 프린세스 메이커 직원 행동 부분",
                "  - Frostpunk(프로스트펑크) 엔딩 부분"};

        Utils.printStringArraySlowly(description, 100);

        BeanRepository beanRepository = new BeanRepository();
        EmployeeRepository employeeRepository = new EmployeeRepository();

        CafeFacade cafeFacade = new CafeFacadeImpl(beanRepository, employeeRepository);

        CafeSimulation cf = new CafeSimulation(cafeFacade);

        cf.run();
    }
}
