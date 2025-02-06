<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>ChannelIO 채팅 인터페이스</title>
  </head>
  <body>
    <!-- ChannelIO SDK 스크립트 삽입 (직접 로드 방식) -->
    <script type="text/javascript">
      (function(){
        var w = window;
        if(w.ChannelIO){
          return w.console.error("ChannelIO script included twice.");
        }
        var ch = function(){ ch.c(arguments); };
        ch.q = [];
        ch.c = function(args){ ch.q.push(args); };
        w.ChannelIO = ch;
        function l(){
          if(w.ChannelIOInitialized){ return; }
          w.ChannelIOInitialized = true;
          var s = document.createElement("script");
          s.type = "text/javascript";
          s.async = true;
          s.src = "https://cdn.channel.io/plugin/ch-plugin-web.js";
          var x = document.getElementsByTagName("script")[0];
          if(x.parentNode){ x.parentNode.insertBefore(s, x); }
        }
        if(document.readyState === "complete"){
          l();
        } else {
          w.addEventListener("DOMContentLoaded", l);
          w.addEventListener("load", l);
        }
      })();
    </script>

    <!-- ChannelIO 초기화 (boot) -->
    <script type="text/javascript">
      ChannelIO('boot', {
        "pluginKey": "${pluginKey}", // 컨트롤러에서 전달된 플러그인 키
        "memberId": "${memberId}"    // 로그인한 사용자 ID (없으면 빈 문자열)
        // 필요 시 추가 프로필 정보도 함께 전달할 수 있습니다.
      });
    </script>

    <!-- 페이지의 기타 콘텐츠 (필요에 따라 추가) -->
    <h1>ChannelIO 채팅룸</h1>
    <p>채팅 위젯이 아래에 표시됩니다.</p>
  </body>
</html>
