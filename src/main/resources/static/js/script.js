function openAddressPopup() { // 주소 팝업 생성
    var pop = window.open("/jusoPopup", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");
}

function jusoCallBack(roadFullAddr) { // 주소 선택시 값 반환
    document.getElementById("address").innerText = roadFullAddr;

    var addressParts = roadFullAddr.split(' ');
    console.log(addressParts);
    var region = addressParts[0]; // 시도명
    var subregion = addressParts[1]; // 시군구명
    var fullRegion = getSidoFullName(region) + ' ' + subregion;
    document.getElementById('korea-map').style.display = 'none';
    msgBox.style.visibility = 'visible';
    loadMessageByRegion(fullRegion);

}

// 초기 대한민국 지도 클릭 이벤트 설정
function initKoreaMapClickEvents() {
    const koreaPaths = document.querySelectorAll('#korea-map path');
    koreaPaths.forEach((path) => {
        path.addEventListener('click', () => handleKoreaMapClick(path));
    });
}

// 초기 대한민국 지도 hover시 h1 텍스트 지역명으로 변경
function initKoreaMapHoverEvents() {
    const koreaPaths = document.querySelectorAll('#korea-map path');
    const headerH1Text = document.getElementById('headerH1Text');
    koreaPaths.forEach((path) => {
        path.addEventListener('mouseenter', () => {
            const id = path.getAttribute('id');
            console.log(id);
            headerH1Text.textContent = getRegionNameFromId(id);
        });
        path.addEventListener('mouseleave', () => {headerH1Text.textContent = '긴급재난문자'});
    });
}

// 대한민국 지도 클릭 시 처리
function handleKoreaMapClick(path) {
    const id = path.getAttribute('id');
    const regionName = getRegionNameFromId(id);
    console.log(`${regionName} 클릭됨`);

    document.getElementById('korea-map').style.display = 'none';
}

// 선택한 지역의 상세 지도 불러오기
async function loadRegionMap(region) {
    try {
        const regionName = getRegionNameFromId(region);
        const response = await fetch(`/map?region=${encodeURIComponent(region)}`);
        const data = await response.text();
        document.getElementById('region-map').innerHTML = data;
        document.getElementById('region-map').style.display = 'block';
        initRegionMapClickEvents(regionName);
        initRegionMapHoverEvents(regionName);
        console.log("loadMap 전달 : " + region);
    } catch (error) {
        console.error('Error loading the map:', error);
    }
}

// 상세 지도 클릭 이벤트 설정
function initRegionMapClickEvents(parentRegion) {
    const regionPaths = document.querySelectorAll('#region-map path');
    regionPaths.forEach((path) => {
        path.addEventListener('click', () => handleRegionMapClick(path, parentRegion));
    });
}

// 상세 지도 hover 이벤트 설정
function initRegionMapHoverEvents(parentRegion) {
    const regionPaths = document.querySelectorAll('#region-map path');
    const headerH1Text = document.getElementById('headerH1Text');
    regionPaths.forEach((path) => {
        const id = path.getAttribute('id');
        console.log(id);
        path.addEventListener('mouseenter', () => {
            headerH1Text.textContent = getRegionNameFromId(id);
        });
        path.addEventListener('mouseleave', () => {
            headerH1Text.textContent = '긴급재난문자';
        });
    });
}

// 하위 지역 클릭 시 처리
function handleRegionMapClick(path, parentRegion) {
    document.querySelectorAll('#region-map path').forEach(p => p.classList.remove('clicked'));
    path.classList.add('clicked');

    const id = path.getAttribute('id');
    const fullRegionName = `${parentRegion} ${id}`;
    console.log(`${fullRegionName} 클릭됨`);

    updateRegionUI(fullRegionName);
    loadMessageByRegion(fullRegionName);
}

// UI 업데이트
function updateRegionUI(fullRegionName) {
    const regionMap = document.getElementById('region-map');
    regionMap.style.left = '25%';
    regionMap.style.transform = 'translate(-50%, -50%)';
    regionMap.style.zIndex = '2';

    const msgBox = document.getElementById('msgBox');
    msgBox.style.visibility = 'visible';
    msgBox.style.left = 'calc(25% + 660px)';
    msgBox.style.zIndex = '1';

    document.getElementById("address").innerText = fullRegionName.trim();
}

// 재난 메시지 서버에서 가져오기
async function loadMessageByRegion(fullRegionName) {
    // fullRegionName 파라미터로 컨트롤러 호출
    fetch('/message?region=' + encodeURIComponent(fullRegionName))
        .then(response => response.json())
        .then(model => {
            console.log(model);
            const messagesWrapper = document.querySelector('.messages-wrapper');

            if (!model.hasMessages) {
                messagesWrapper.innerHTML = `
        <div class="no-message">
          <p>${model.errorMessage}</p>
        </div>
      `;
            } else {
                messagesWrapper.innerHTML = ''; // 기존 내용 비우기
                model.messageList.forEach(message => {
                    const messageBox = document.createElement('div');
                    // 긴급 레벨에 따른 클래스 설정 (안전안내 = level1로 가정)
                    messageBox.className = `message-box emergency-level1`;

                    messageBox.innerHTML = `
          <div class="message-header">
            <span class="disaster-type">${message.dstSeNm}</span>
            <span class="emergency-level">${message.emegstepNm}</span>
          </div>
          <div class="message-content">
            <p>${message.msgCn}</p>
          </div>
          <div class="message-footer">
            <span class="region">${message.rcptnRgnNm}</span>
            <span class="date">발송일시: ${formatDate(message.crtDt)}</span>
          </div>
        `;

                    messagesWrapper.appendChild(messageBox);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.querySelector('.messages-wrapper').innerHTML = `
      <div class="error-message">
        <p>메시지를 불러오는 중 오류가 발생했습니다.</p>
      </div>
    `;
        });

// 날짜 포맷팅 함수
    function formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }
}

// 지역 ID를 한글 지역명으로 변환
function getRegionNameFromId(id) {
    const regionNames = {
        "gangwon": "강원도", "seoul": "서울특별시", "busan": "부산광역시",
        "incheon": "인천광역시", "daegu": "대구광역시", "daejeon": "대전광역시",
        "ulsan": "울산광역시", "gwangju": "광주광역시", "gyeonggi": "경기도",
        "chungbuk": "충청북도", "chungnam": "충청남도", "jeonbuk": "전라북도",
        "jeonnam": "전라남도", "gyeongbuk": "경상북도", "gyeongnam": "경상남도",
        "jeju": "제주특별자치도", "sejong": "세종특별자치시"
    };
    return regionNames[id] || id;
}
function getSidoFullName(region) {
    const regionNames = {
        "강원": "강원도", "서울": "서울특별시", "부산": "부산광역시",
        "인천": "인천광역시", "대구": "대구광역시", "대전": "대전광역시",
        "울산": "울산광역시", "광주": "광주광역시", "경기": "경기도",
        "충북": "충청북도", "충남": "충청남도", "전북": "전라북도",
        "전남": "전라남도", "경북": "경상북도", "경남": "경상남도",
        "제주": "제주특별자치도", "세종": "세종특별자치시"
    };
    return regionNames[region] || region;
}
// 초기화
document.addEventListener('DOMContentLoaded', () => {
    initKoreaMapClickEvents();
    initKoreaMapHoverEvents();
});


const xButton = document.getElementById('xButton');
xButton.style.display = 'block';
xButton.addEventListener('click', () => {
    window.location.reload();
});
function showAbout() {
    xButton.style.display = 'block';
    document.getElementById('korea-map').style.display = 'none';
    document.getElementById('about').style.display = 'block';
}
