import React from 'react';
import '../css/Loader.css';

const Loader = () => {
  return (
    <>
    <div className="loader">
      <span className="hour"></span>
      <span className="min"></span>
      <span className="circel"></span>
    </div>
    <div className='loading'>
      <p>Loading...</p>
    </div>
    </>
  );
};

export default Loader;
