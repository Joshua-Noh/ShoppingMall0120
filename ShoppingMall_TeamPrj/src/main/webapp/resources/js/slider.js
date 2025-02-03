document.addEventListener('DOMContentLoaded', () => {
  const slides = document.querySelector('.slides'); // 슬라이더의 전체 슬라이드 컨테이너
  const slide = document.querySelectorAll('.slide'); // 각 슬라이드
  const prevBtn = document.querySelector('.prev-btn'); // 이전 버튼
  const nextBtn = document.querySelector('.next-btn'); // 다음 버튼
  let currentIndex = 0; // 현재 슬라이드의 시작 인덱스
  const visibleSlides = 5; // 화면에 보이는 슬라이드 개수
  const totalSlides = slide.length; // 총 슬라이드 개수

  // 다음 버튼 이벤트
  nextBtn.addEventListener('click', () => {
    if (currentIndex < totalSlides - visibleSlides) {
      currentIndex++;
      slides.style.transform = `translateX(-${currentIndex * (100 / visibleSlides)}%)`;
    }
  });

  // 이전 버튼 이벤트
  prevBtn.addEventListener('click', () => {
    if (currentIndex > 0) {
      currentIndex--;
      slides.style.transform = `translateX(-${currentIndex * (100 / visibleSlides)}%)`;
    }
  });

  // 자동 슬라이드 이동
  setInterval(() => {
    if (currentIndex < totalSlides - visibleSlides) {
      currentIndex++;
    } else {
      currentIndex = 0; // 마지막 슬라이드에서 처음으로 이동
    }
    slides.style.transform = `translateX(-${currentIndex * (100 / visibleSlides)}%)`;
  }, 10000); // 10초마다 자동 이동
});
