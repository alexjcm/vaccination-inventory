import React, {useRef, useContext} from 'react';
import {Button} from 'primereact/button';
import {Menu} from 'primereact/menu';
import {Avatar} from 'primereact/avatar';
import Context from '../context/UserContext';

interface TopBarProps {
  onMenuToggle: () => void;
  activeView: string;
}

export default function TopBar({ onMenuToggle, activeView }: TopBarProps) {
  const { authService } = useContext(Context);
  const menu = useRef<Menu>(null);

  const items = [
    {
      label: 'My Account',
      items: [
        { label: 'Profile Settings', icon: 'pi pi-user-edit' },
        { 
          label: 'Logout', 
          icon: 'pi pi-sign-out', 
          command: () => authService?.logout() 
        }
      ]
    }
  ];

  const viewLabels: Record<string, string> = {
    'employees': 'Employee Management',
    'vaccines': 'Vaccine Catalog'
  };

  const userName = authService?.user?.name || 'Administrator';
  const userPicture = authService?.user?.picture;

  // Modern PT styling for the popup menu
  const ptMenu = {
    root: { style: { background: 'white', border: '1px solid #e2e8f0', boxShadow: '0 10px 15px -3px rgba(0, 0, 0, 0.1)' } },
    menuitem: { className: 'hover:surface-100' },
    action: { className: 'text-slate-600 font-medium' }
  };

  return (
    <div className="layout-topbar">
      <div className="topbar-left">
        <Button icon="pi pi-bars" className="p-button-text p-button-secondary" onClick={onMenuToggle} />
        
        <div className="layout-breadcrumb">
          <span className="font-brand font-bold text-navy tracking-tight" style={{ fontSize: '1.2rem' }}>MediStock</span>
          <i className="pi pi-chevron-right breadcrumb-separator"></i>
          <span className="text-slate-400 font-medium">Dashboard</span>
          <i className="pi pi-chevron-right breadcrumb-separator"></i>
          <span className="text-navy font-semibold">{viewLabels[activeView] || 'Overview'}</span>
        </div>
      </div>

      <div className="topbar-right">
        <Menu model={items} popup ref={menu} id="popup_menu" appendTo="self" pt={ptMenu} />
        <div className="topbar-user-profile" onClick={(e) => menu.current?.toggle(e)}>
          <Avatar 
            image={userPicture} 
            icon={!userPicture ? 'pi pi-user' : undefined} 
            shape="circle" 
            className="bg-slate-100 text-navy"
            style={{ width: '2.3rem', height: '2.3rem' }}
          />
          <div className="hidden md:flex flex-column ml-2" style={{ lineHeight: '1.2' }}>
            <span className="text-slate-900 font-bold text-sm">{userName}</span>
            <small className="text-slate-400 text-xs font-semibold uppercase tracking-wider">Administrator</small>
          </div>
          <i className="pi pi-chevron-down text-slate-400 text-xs ml-3"></i>
        </div>
      </div>
    </div>
  );
}
