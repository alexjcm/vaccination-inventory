import React, { useState } from 'react';
import Sidebar from './Sidebar';
import TopBar from './TopBar';

interface DashboardLayoutProps {
  children: React.ReactNode;
  activeView: string;
  onViewChange: (view: string) => void;
}

export default function DashboardLayout({ children, activeView, onViewChange }: DashboardLayoutProps) {
  const [layoutState, setLayoutState] = useState({
    staticMenuDesktopInactive: false,
    overlayMenuActive: false,
    mobileMenuActive: false,
    configSidebarVisible: false
  });

  const onMenuToggle = () => {
    if (isDesktop()) {
      setLayoutState((prev) => ({ ...prev, staticMenuDesktopInactive: !prev.staticMenuDesktopInactive }));
    } else {
      setLayoutState((prev) => ({ ...prev, mobileMenuActive: !prev.mobileMenuActive }));
    }
  };

  const isDesktop = () => {
    return window.innerWidth > 991;
  };

  const containerClassName = `layout-wrapper ${layoutState.staticMenuDesktopInactive ? 'layout-sidebar-inactive' : ''} ${layoutState.mobileMenuActive ? 'layout-mobile-active' : ''}`;

  return (
    <div className={containerClassName}>
      <TopBar onMenuToggle={onMenuToggle} activeView={activeView} />
      <Sidebar activeView={activeView} onViewChange={(v) => { onViewChange(v); if (!isDesktop()) setLayoutState(prev => ({ ...prev, mobileMenuActive: false })); }} />
      
      <div className="layout-main-container">
        <div className="layout-main">
          {children}
        </div>
      </div>
      
      {/* Mask for mobile menu overlay */}
      <div className="layout-mask" onClick={() => setLayoutState(prev => ({ ...prev, mobileMenuActive: false }))}></div>
    </div>
  );
}
