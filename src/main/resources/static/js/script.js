function openAddressPopup() { // 주소 팝업 생성
    var pop = window.open("/jusoPopup", "pop", "width=570,height=420, scrollbars=yes, resizable=yes");
}

function jusoCallBack(roadFullAddr) { // 주소 선택시 값 반환
    document.getElementById("address").innerText = roadFullAddr;

    var addressParts = roadFullAddr.split(' ');
    var region = addressParts[0]; // 시도명
    var subregion = addressParts[1]; // 시군구명

    fetch("/disaster", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            'region': region,
            'subregion': subregion
        })
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("disaster-message").innerText = data;
    })
    .catch(error => console.error('Error:', error));
}

function loadMap(region) {
    console.log("loadmap : " + region);
    // 한글 지역명으로 변환하여 전달
    const regionName = getRegionNameFromId(encodeURIComponent(region)); // region id를 한글로 변환
    console.log("regionName : " + regionName);
    fetch(`/map?region=${region}`)
    .then(response => response.text())
    .then(data => {
        document.getElementById('region-map').innerHTML = data;
        addClickEventToPaths(regionName);
    })
    .catch(error => console.error('Error loading the map:', error));
}

function addClickEventToPaths(parentRegion = '') {
    const koreaPaths = document.querySelectorAll('#korea-map path');
    koreaPaths.forEach((path) => {
        path.addEventListener('click', () => {
            const id = path.getAttribute('id');
            const regionName = getRegionNameFromId(id);
            console.log(`${regionName} 클릭됨`);

            document.getElementById('korea-map').style.display = 'none';
            document.getElementById('region-map').style.display = 'block';
            console.log("loadMap 전달 : " + regionName);
        });
    });

    const regionPaths = document.querySelectorAll('#region-map path');
    regionPaths.forEach((path) => {
        path.addEventListener('click', () => {
            regionPaths.forEach(p => p.classList.remove('clicked'));

            path.classList.add('clicked');

            const id = path.getAttribute('id');
            const fullRegionName = `${parentRegion} ${id}`;
            console.log(`${fullRegionName} 클릭됨`);

            const regionMap = document.getElementById('region-map');
            regionMap.style.left = '25%';
            regionMap.style.transform = 'translate(-50%, -50%)';
            regionMap.style.zIndex = '2';

            const msgBox = document.getElementById('msgBox');
            msgBox.style.visibility = 'visible';
            msgBox.style.left = 'calc(25% + 660px)';
            msgBox.style.zIndex = '1';
            
            document.getElementById("address").innerText = fullRegionName.trim();

            const region = parentRegion;
            const subregion = id;
            fetch("/disaster", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    'region': region,
                    'subregion': subregion
                })
            })
            .then(response => response.text())
            .then(data => {
                document.getElementById("disaster-message").innerText = data;
            })
            .catch(error => console.error('Error:', error));
        });
    });
}

function getRegionNameFromId(id) {
    const regionNames = {
        "gangwon": "강원도",
        "seoul": "서울특별시",
        "busan": "부산광역시",
        "incheon": "인천광역시",
        "daegu": "대구광역시",
        "daejeon": "대전광역시",
        "ulsan": "울산광역시",
        "gwangju": "광주광역시",
        "gyeonggi": "경기도",
        "chungbuk": "충청북도",
        "chungnam": "충청남도",
        "jeonbuk": "전라북도",
        "jeonnam": "전라남도",
        "gyeongbuk": "경상북도",
        "gyeongnam": "경상남도",
        "jeju": "제주특별자치도",
        "sejong": "세종특별자치시"
    };
    return regionNames[id] || id;
}

document.addEventListener('DOMContentLoaded', () => {
    addClickEventToPaths();

    const xButton = document.getElementById('xButton');
    xButton.style.display = 'block';
    xButton.addEventListener('click', () => {
        window.location.reload();
    });
});
