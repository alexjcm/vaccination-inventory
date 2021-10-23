import React from 'react';
import Users from '../components/Users';

export default function Home() {
  return (
    <>
      <header className="o-header"></header>
      <div className="App-wrapper">
        <div className="App-main">HOME</div>
        <Users />
      </div>
    </>
  );
}
