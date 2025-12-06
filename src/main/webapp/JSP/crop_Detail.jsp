<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GardenLog | ${cropDetail.crop_title} 상세 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/crop_Detail.css">
</head>
<body>

    <%@ include file="header.jsp" %>
    
    <div class="container">
        
        <div class="main-content-wrapper">

            <div class="left-main-area">

                <section class="crop-header-info">
                    <div class="crop-main-info">
                        <div class="crop-image-placeholder">
                            </div>
                        <div>
                            <h1>${cropDetail.crop_title}</h1>
                            <span class="tag tag-fruit">${cropDetail.classification}</span>
                            <span class="tag tag-status">재배 중</span>
                            <p class="description">
                                ${cropDetail.crop_title}의 학명은 ${cropDetail.scientificName}이며 ${cropDetail.classification}에 속합니다.
                            </p>
                            <div class="action-buttons">
                                <button class="action-btn-add">내 텃밭에 추가</button>
                                <button class="action-btn-wish">관심 작물</button>
                            </div>
                        </div>
                    </div>
                </section>

                <section class="status-section">
                    <h2>재배 현황</h2>
                    <div class="status-cards">
                        <div class="status-card">D-120<p>예상 수확일</p></div>
                        <div class="status-card">1단계<p>생장 단계</p></div>
                        <div class="status-card">5단계<p>배양 단계</p></div>
                    </div>
                </section>
                
                <section class="guide-section">
                    <h2>재배 가이드</h2>
                    <div class="guide-tabs">
                        <button class="tab-button active">기본 정보</button>
                    </div>

                    <div class="tab-content basic-info-content active">
                        <div class="info-grid">
                            <p>✅ 재배 기간: <span>${cropDetail.period}</span></p>
                            <p>🌡️ 생육 적온: <span>${cropDetail.suitableTempGrowth}</span></p>
                            <p>💧 발아 적온: <span>${cropDetail.suitableTempGermination}</span></p>
                            <p>🌱 토양 환경: <span>${cropDetail.suitableSoil}</span></p>
                        </div>
                        
                        <div class="info-block">
                            <h4>씨뿌림 상세 정보 및 일정</h4>
                            <pre class="pre-formatted">${cropDetail.sowingInfo}</pre>

                            <h4>작형별 출하시기 (표)</h4>
                            <div class="table-container">${cropDetail.plantingSchedule}</div>
                            
                            <h4>생리적 특성 및 주요 기술</h4>
                            <pre class="pre-formatted">
                                [생리적 특성]
                                ${cropDetail.physiologicalFeatures}
                                
                                [주요 기술]
                                ${cropDetail.mainTechniques}
                            </pre>
                        </div>
                    </div>
                </section>
            </div>

            <aside class="sidebar-section">
                
                <div class="quick-info-box">
                    <p class="box-title">빠른 정보</p>
                    <div class="info-item"><span>분류</span><span class="value">${cropDetail.classification}</span></div>
                    <div class="info-item"><span>발아 적온</span><span class="value">${cropDetail.suitableTempGermination}</span></div> 
                    <div class="info-item"><span>생육 적온</span><span class="value">${cropDetail.suitableTempGrowth}</span></div>
                    <div class="info-item"><span>수확 정보</span><span class="value">상세 가이드 참조</span></div>
                    <div class="info-item"><span>토양 환경</span><span class="value">${cropDetail.suitableSoil}</span></div>
                </div>

                <div class="tip-box">
                    <h3>💡 재배 팁</h3>
                    <pre class="pre-formatted-tip">
                        [주요 관리]
                        ${cropDetail.managementInfo}
                        
                        [재해 대책]
                        ${cropDetail.disasterCountermeasures}
                    </pre>
                </div>
                
                </aside>
        </div>
    </div>
</body>
</html>