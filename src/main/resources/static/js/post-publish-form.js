document.addEventListener('DOMContentLoaded', function () {
    const tagInput = document.getElementById('tag');
    const tagContainer = document.getElementById('tag-container');
    const tags = [];
    let isComposing = false;

    tagInput.addEventListener('compositionstart', function () {
        isComposing = true;
    });

    tagInput.addEventListener('compositionend', function () {
        isComposing = false;
    });

    tagInput.addEventListener('keydown', function (e) {
        if (!isComposing && (e.key === 'Enter' || e.key === ',')) {
            e.preventDefault();
            const tagValue = tagInput.value.trim();
            if (tagValue) {
                addTag(tagValue);
                tagInput.value = '';
            }
        }
    });

    function addTag(tag) {
        if (tag && !tags.includes(tag)) {
            tags.push(tag);
            updateTagsInput();

            const tagBtn = document.createElement('button');
            tagBtn.type = 'button';
            tagBtn.className = 'btn tag-btn btn-outline-dark position-relative me-1 mb-1';
            tagBtn.textContent = tag;

            tagBtn.addEventListener('click', function () {
                removeTag(tag);
                tagContainer.removeChild(tagBtn);
            });
            tagContainer.appendChild(tagBtn);
        }
    }

    function removeTag(tag) {
        const index = tags.indexOf(tag);
        if (index > -1) {
            tags.splice(index, 1);
            updateTagsInput();
        }
    }

    function updateTagsInput() {
        document.getElementById('tags').value = tags.join(',');
    }

    document.getElementById('tempSaveBtn').addEventListener('click', function () {
        document.getElementById('isTemp').value = true;
        document.getElementById('isPrivate').value = true;
        document.getElementById('mainForm').submit();
    });

    document.getElementById('publishBtn').addEventListener('click', function () {
        document.getElementById('isTemp').value = false;

        // 컨텐츠 내용 미리보기 설정
        const content = document.getElementById('content').value;
        document.getElementById('contentPreview').value = content.substring(0, 150);
    });

    // 실시간 미리보기 기능 추가
    const titleInput = document.getElementById('title');
    const contentInput = document.getElementById('content');
    const previewTitle = document.getElementById('preview-title');
    const previewContent = document.getElementById('preview-content');

    titleInput.addEventListener('input', function () {
        previewTitle.textContent = titleInput.value;
    });

    contentInput.addEventListener('input', function () {
        previewContent.textContent = contentInput.value;
    });

    // 썸네일 업로드 처리
    const thumbnailForm = document.getElementById('thumbnailForm');
    const thumbnailWrapper = document.getElementById('thumbnailWrapper');
    const thumbnailActions = document.querySelector('.thumbnail-actions');

    function resetThumbnailButton() {
        thumbnailWrapper.innerHTML = `<button type="button" id="thumbnailUploadBtn" class="btn btn-secondary">썸네일 업로드</button>`;
        thumbnailActions.style.display = 'none';
        thumbnailForm.value = '';
        document.getElementById('thumbnailUploadBtn').addEventListener('click', function () {
            thumbnailForm.click();
        });
    }

    document.getElementById('thumbnailUploadBtn').addEventListener('click', function () {
        thumbnailForm.click();
    });

    thumbnailForm.addEventListener('change', function () {
        if (thumbnailForm.files && thumbnailForm.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                thumbnailWrapper.innerHTML = `<img src="${e.target.result}" alt="썸네일" style="width: 100%; height: 100%; object-fit: cover;">`;
                thumbnailActions.style.display = 'block';
            };
            reader.readAsDataURL(thumbnailForm.files[0]);
        }
    });

    document.getElementById('reuploadThumbnail').addEventListener('click', function () {
        thumbnailForm.click();
    });

    document.getElementById('removeThumbnail').addEventListener('click', function () {
        resetThumbnailButton();
    });

    // 모달창의 출간하기 버튼 클릭 시 모달 데이터 메인 폼으로 복사 후 제출
    document.getElementById('modalPublishBtn').addEventListener('click', function () {
        const visibility = document.getElementById('visibility').value;

        document.getElementById('isPrivate').value = visibility === 'true';

        document.getElementById('mainForm').submit();
    });

    // 시리즈 설정 처리
    const seriesInput = document.getElementById('seriesInput');
    const seriesRegisterBtn = document.getElementById('seriesRegisterBtn');
    const seriesArea = document.getElementById('seriesArea');
    const seriesList = document.getElementById('seriesList');
    const newSeriesInput = document.getElementById('newSeriesInput');
    const newSeriesActions = document.getElementById('newSeriesActions');
    const cancelNewSeriesBtn = document.getElementById('cancelNewSeriesBtn');
    const addNewSeriesBtn = document.getElementById('addNewSeriesBtn');
    const cancelSeriesBtn = document.getElementById('cancelSeriesBtn');
    const selectSeriesBtn = document.getElementById('selectSeriesBtn');

    seriesRegisterBtn.addEventListener('click', function () {
        const blogId = document.querySelector('[name="blogId"]').value;

        fetch(`http://localhost:8080/series/${blogId}/all`)
            .then(response => response.json())
            .then(response => {
                const data = response.data; // Message 객체에서 data 부분만 추출합니다.
                if (Array.isArray(data)) {
                    seriesList.innerHTML = ''; // Clear any existing list items

                    data.forEach(series => {
                        const listItem = document.createElement('li');
                        listItem.className = 'list-group-item';

                        const seriesRadio = document.createElement('input');
                        seriesRadio.type = 'radio';
                        seriesRadio.name = 'series';
                        seriesRadio.value = series.name;

                        listItem.appendChild(seriesRadio);
                        listItem.appendChild(document.createTextNode(series.name));
                        seriesList.appendChild(listItem);
                    });

                    seriesArea.classList.remove('d-none'); // Show series area
                } else {
                    console.error('Received data is not an array:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching series:', error);
            });
    });

    newSeriesInput.addEventListener('input', function () {
        if (newSeriesInput.value) {
            newSeriesActions.classList.remove('d-none');
        } else {
            newSeriesActions.classList.add('d-none');
        }
    });

    addNewSeriesBtn.addEventListener('click', function () {
        const blogId = document.querySelector('[name="blogId"]').value;

        fetch(`http://localhost:8080/series/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name: newSeriesInput.value, blogId: blogId })
        })
            .then(response => response.json())
            .then(responseData => {
                if (responseData.data) {
                    const newSeries = responseData.data;
                    const newListItem = document.createElement('li');
                    newListItem.className = 'list-group-item';

                    const newSeriesRadio = document.createElement('input');
                    newSeriesRadio.type = 'radio';
                    newSeriesRadio.name = 'series';
                    newSeriesRadio.value = newSeries.name;

                    newListItem.appendChild(newSeriesRadio);
                    newListItem.appendChild(document.createTextNode(newSeries.name));
                    seriesList.appendChild(newListItem);

                    newSeriesInput.value = '';
                    newSeriesActions.classList.add('d-none');
                } else {
                    console.error('Failed to add new series:', responseData.message);
                }
            })
            .catch(error => {
                console.error('Error fetching series:', error);
            });
    });

    cancelNewSeriesBtn.addEventListener('click', function () {
        newSeriesInput.value = '';
        newSeriesActions.classList.add('d-none');
    });

    cancelSeriesBtn.addEventListener('click', function () {
        seriesArea.classList.add('d-none');
    });

    selectSeriesBtn.addEventListener('click', function () {
        const selectedSeries = document.querySelector('input[name="series"]:checked');
        if (selectedSeries) {
            seriesInput.value = selectedSeries.value;
            seriesArea.classList.add('d-none');
        }
    });
});