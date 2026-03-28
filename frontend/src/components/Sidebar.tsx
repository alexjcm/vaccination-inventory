import React from 'react';

interface SidebarProps {
  activeView: string;
  onViewChange: (view: string) => void;
}

export default function Sidebar({ activeView, onViewChange }: SidebarProps) {
  const menuItems = [
    { label: 'Employee Management', icon: 'pi pi-users', id: 'employees' },
    { label: 'Vaccine Catalog', icon: 'pi pi-box', id: 'vaccines' }
  ];

  return (
    <div className="layout-sidebar">
      <ul className="layout-menu">
        {menuItems.map((item) => (
          <li key={item.id}>
            <div 
              className={`layout-menu-item p-ripple ${activeView === item.id ? 'active-item' : ''}`}
              onClick={() => onViewChange(item.id)}
            >
              <i className={item.icon}></i>
              <span>{item.label}</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
