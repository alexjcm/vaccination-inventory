import { useContext } from 'react';
import Context from '../context/UserContext';
import Header from './Header';

export default function LandingView() {
  const { authService } = useContext(Context);

  return (
    <div className="App-main unauthenticated">
      <Header />
      <div className="hero-section corporate-container">
        {/* Mobile-First Layout */}
        <div className="hero-layout-mobile visible-mobile">
          <div className="hero-content">
            <h1 className="hero-title text-gradient-navy">Manage your<br/>Inventory with<br/>Excellence</h1>
            <p className="hero-subtitle">The leading platform for tracking and monitoring vaccination schemes nationwide.</p>
            <button className="btn-corporate-primary" onClick={() => authService?.login()}>
              Get Started
            </button>
          </div>
        </div>

        {/* Desktop Layout */}
        <div className="hero-layout-desktop visible-desktop">
          <div className="hero-content">
            <h1 className="hero-title text-gradient-navy">Vaccination Inventory<br/>Management</h1>
            <p className="hero-subtitle">Visualize, track, and optimize your vaccine inventory with enterprise-grade security and real-time reporting.</p>
            <div className="cta-group">
              <button className="btn-corporate-primary" onClick={() => authService?.login()}>
                Login to System
              </button>
            </div>
          </div>
          
          <div className="hero-visual">
            <div className="visual-card-premium">
               <div className="visual-graphic">
                 <img src="https://images.unsplash.com/photo-1584432810601-6c7f27d2362b?auto=format&fit=crop&q=80&w=200" alt="Clinical Symbol" className="visual-icon" />
               </div>
              <div className="visual-text">
                <span className="visual-label">System Status</span>
                <span className="visual-value">Active</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="feature-grid corporate-container">
        <div className="feature-card card-corporate">
          <div className="feature-icon-box">
            <i className="pi pi-shield"></i>
          </div>
          <h3>Total Security</h3>
          <p>Strict compliance with privacy standards and detailed audit logs for every transaction.</p>
        </div>
        <div className="feature-card card-corporate">
          <div className="feature-icon-box">
            <i className="pi pi-chart-line"></i>
          </div>
          <h3>Real-Time Tracking</h3>
          <p>Dynamic monitoring of inventories and expiration dates across all your locations.</p>
        </div>
        <div className="feature-card card-corporate">
          <div className="feature-icon-box">
            <i className="pi pi-server"></i>
          </div>
          <h3>Unified Management</h3>
          <p>Centralize your staff information and vaccine catalog in a single enterprise portal.</p>
        </div>
      </div>
    </div>
  );
}
