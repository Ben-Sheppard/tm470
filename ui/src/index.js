import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/index.scss';
import MyMoodHeader from "./components/MyMoodHeader";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Faq from "./pages/Faq";
import Analysis from "./pages/Analysis"
import Api from "./utils/Api";
import HeadlineCounts from "./components/HeadlineCounts";
import HistoricChart from "./components/HistoricChart";
import EmotionCounts from "./components/EmotionCounts";

const root = ReactDOM.createRoot(document.getElementById('root'));
const api = new Api();
const myMoodHeader = <MyMoodHeader/>;
const headlineCounts = <HeadlineCounts api={api} />
const historicChart = <HistoricChart api={api}/>
const emotionCounts = <EmotionCounts api={api}/>

root.render(
  <React.StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Home myMoodHeader={myMoodHeader} api={api}/>} />
              <Route path="/faq" element={<Faq myMoodHeader={myMoodHeader}/>} />
              <Route path="/analysis" element={
                  <Analysis
                      myMoodHeader={myMoodHeader}
                      headlineCounts={headlineCounts}
                      historicChart={historicChart}
                      emotionCounts={emotionCounts}
                  />
              } />
          </Routes>
      </BrowserRouter>
  </React.StrictMode>
);
