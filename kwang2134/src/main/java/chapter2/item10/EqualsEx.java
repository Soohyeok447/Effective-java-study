package chapter2.item10;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class EqualsEx {

    /**
     * 대칭성 위반 예시
     * CaseInsensitiveString 과 String을 비교하는 경우 문제 발생
     * caseInsensitiveString.equals(string) 비교 시 String 타입과도 비교할 수 있게 구현되어 true가 출력되나
     * string.equals(caseInsensitiveString) 비교 시 오직 String 타입만 비교하도록 구현되어 타입 비교에서 false 반환
     */
    class CaseInsensitiveString {
        private final String str;

        public CaseInsensitiveString(String str) {
            this.str = Objects.requireNonNull(str);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CaseInsensitiveString) {
                return str.equalsIgnoreCase(((CaseInsensitiveString) obj).str);
            }
            // 문제 발생 지점
            if(obj instanceof String) {
                return str.equalsIgnoreCase((String) obj);
            }
            return false;
        }
    }


    class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Point)) {
                return false;
            }
            Point p = (Point) obj;
            return p.x == x && p.y == y;
        }
    }

    /**
     * 추이성 위반 예시
     * Point 와 ColorPoint 비교 시 색 비교 없이 비교를 수행
     * ColorPoint 끼리 비교 시 색 비교까지 수행
     * Point p1, ColorPoint p2, ColorPoint p3 가 있을 때
     * p1.equals(p2) = true
     * p1.equals(p3) = true 일 때
     * p2.equals(p3) = false 가 나올 수 있음
     */
    class ColorPoint extends Point {
        private final Color color;

        public ColorPoint(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Point)) {
                return false;
            }

            // 문제 발생 지점
            if(!(obj instanceof ColorPoint)) {
                return obj.equals(this);
            }

            // 문제 발생 지점
            return super.equals(obj) && ((ColorPoint) obj).color == color;
        }
    }

    /**
     * 일관성 위반 예시
     * 네트워크 상태에 따라 결과가 매번 달라질 수 있음
     * 같은 객체 비교 결과가 매번 달라진다면 일관성 위반
     */
    class InconsistentUrl {
        private final URL url;

        public InconsistentUrl(String url) throws MalformedURLException {
            this.url = new URL(url);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof InconsistentUrl)) {
                return false;
            }

            try {
                return url.openConnection().getURL().equals(
                        ((InconsistentUrl) obj).url.openConnection().getURL()
                );
            } catch (IOException e) {
                return false;
            }
        }
    }

    private final String compareField1;
    private final String compareField2;

    public EqualsEx(String compareField1, String compareField2) {
        this.compareField1 = compareField1;
        this.compareField2 = compareField2;
    }

    /**
     * 양질의 equals 메서드 구현 4단계
     */
    @Override
    public boolean equals(Object obj) {
        // 1단계: == 연산자로 입력이 자기 자신의 참조인지 확인
        if (this == obj) return true;

        // 2단계: instanceof 연산자로 입력이 올바른 타입인지 확인
        if(!(obj instanceof EqualsEx)) return false;

        // 3단계: 입력을 올바른 타입으로 형변환
        EqualsEx other = (EqualsEx) obj;

        // 4단계: 입력 객체와 자기 자신의 대응되는 핵심 필드들이 모두 일치하는지 확인
        return compareField1.equals(other.compareField1) && compareField2.equals(other.compareField2);
    }
}
