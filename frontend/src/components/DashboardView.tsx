import { useState, useRef } from 'react';
import * as ExcelJS from 'exceljs';
import { saveAs } from 'file-saver';

// PrimeReact Components
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dialog } from 'primereact/dialog';
import { Toast } from 'primereact/toast';
import { Toolbar } from 'primereact/toolbar';
import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import { Tag } from 'primereact/tag';
import { Dropdown } from 'primereact/dropdown';
import { InputNumber } from 'primereact/inputnumber';
import { FilterMatchMode } from 'primereact/api';

// Business Logic & Hooks
import { useInventory, User, Vaccine, emptyUser, emptyVaccine } from '../hooks/useInventory';
import DashboardLayout from './DashboardLayout';

export default function DashboardView() {
  const { users, vaccines, loading, saveUser, deleteUser, saveVaccine, deleteVaccine } = useInventory();
  
  // UI Navigation State
  const [activeView, setActiveView] = useState<'employees' | 'vaccines'>('employees');
  
  // UI Dialog State
  const [userDialog, setUserDialog] = useState(false);
  const [vaccineDialog, setVaccineDialog] = useState(false);
  const [user, setUser] = useState<User>(emptyUser);
  const [vaccine, setVaccine] = useState<Vaccine>(emptyVaccine);
  const [submitted, setSubmitted] = useState(false);
  
  // Filtering State
  const [filters, setFilters] = useState({
    global: { value: '', matchMode: FilterMatchMode.CONTAINS },
    identCard: { value: '', matchMode: FilterMatchMode.CONTAINS },
    firstName: { value: '', matchMode: FilterMatchMode.STARTS_WITH },
    lastName: { value: '', matchMode: FilterMatchMode.STARTS_WITH },
    email: { value: '', matchMode: FilterMatchMode.CONTAINS }
  });
  
  const toast = useRef<Toast>(null);

  // --- Pass-Through (PT) Configurations for Modern PrimeReact ---
  const ptDialog = {
    root: { className: 'corporate-dialog' },
    mask: { style: { backdropFilter: 'blur(4px)', backgroundColor: 'rgba(15, 23, 42, 0.4)' } },
    header: { style: { background: 'white', borderBottom: '1px solid #f1f5f9' } },
    content: { style: { background: 'white', padding: '2.5rem' } },
    footer: { style: { background: '#f8fafc', borderTop: '1px solid #f1f5f9' } }
  };

  // --- Actions ---
  const handleSaveUser = async () => {
    setSubmitted(true);
    if (user.firstName && user.lastName && user.email) {
      try {
        await saveUser(user);
        setUserDialog(false);
        toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Employee saved', life: 3000 });
      } catch (err: any) {
        toast.current?.show({ severity: 'error', summary: 'Error', detail: err.message, life: 3000 });
      }
    }
  };

  const handleSaveVaccine = async () => {
    setSubmitted(true);
    if (vaccine.name.trim()) {
      try {
        await saveVaccine(vaccine);
        setVaccineDialog(false);
        toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Vaccine saved', life: 3000 });
      } catch (err: any) {
        toast.current?.show({ severity: 'error', summary: 'Error', detail: err.message, life: 3000 });
      }
    }
  };

  const confirmDelete = (item: any, type: 'user' | 'vaccine') => {
    confirmDialog({
      message: `Are you sure you want to delete "${item.name || item.firstName}"?`,
      header: 'Security Confirmation',
      icon: 'pi pi-exclamation-circle',
      appendTo: 'self',
      acceptLabel: 'Confirm Delete',
      rejectLabel: 'Cancel',
      acceptClassName: 'p-button-danger p-button-sm',
      rejectClassName: 'p-button-text p-button-secondary p-button-sm',
      pt: ptDialog,
      accept: async () => {
        try {
          if (type === 'user') await deleteUser(item.id);
          else await deleteVaccine(item.id);
          toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Resource deleted', life: 3000 });
        } catch (err: any) {
          toast.current?.show({ severity: 'error', summary: 'Error', detail: 'Action failed', life: 3000 });
        }
      }
    });
  };

  const exportExcel = async () => {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet(activeView === 'employees' ? 'Employees' : 'Vaccines');

    if (activeView === 'employees') {
      worksheet.columns = [
        { header: 'Identification Number', key: 'identCard', width: 25 },
        { header: 'First Name', key: 'firstName', width: 20 },
        { header: 'Last Name', key: 'lastName', width: 20 },
        { header: 'Email', key: 'email', width: 35 },
        { header: 'Status', key: 'isVaccinated', width: 15 }
      ];
      users.forEach((u) => worksheet.addRow({ ...u, isVaccinated: u.isVaccinated ? 'Yes' : 'No' }));
    } else {
      worksheet.columns = [
        { header: 'Vaccine Brand', key: 'name', width: 35 },
        { header: 'Scientific Category', key: 'type', width: 35 }
      ];
      vaccines.forEach((v) => worksheet.addRow(v));
    }

    worksheet.getRow(1).font = { bold: true };
    const buffer = await workbook.xlsx.writeBuffer();
    saveAs(new Blob([buffer]), `medistock_${activeView}.xlsx`);
  };

  // --- Templates ---
  const statusTemplate = (rowData: User) => (
    <Tag 
      value={rowData.isVaccinated ? 'VACCINATED' : 'NOT VACCINATED'} 
      severity={rowData.isVaccinated ? 'success' : 'danger'} 
      style={{ fontSize: '0.7rem', fontWeight: 'bold' }}
    />
  );

  const vaccineInfoTemplate = (rowData: User) => (
    rowData.vaccine ? (
      <div className="flex flex-column" style={{ gap: '2px' }}>
        <span className="font-bold text-navy text-sm">{rowData.vaccine.name}</span>
        <small className="text-slate-400 font-semibold uppercase tracking-tight" style={{ fontSize: '0.6rem' }}>{rowData.vaccine.type}</small>
      </div>
    ) : <span className="text-slate-300 font-bold">—</span>
  );

  const actionTemplate = (rowData: any, type: 'user' | 'vaccine') => (
    <div className="flex justify-content-end gap-2 pr-2">
      <Button 
        icon="pi pi-pencil" 
        className="btn-action-minimal" 
        onClick={() => {
          if (type === 'user') { setUser({ ...rowData }); setUserDialog(true); }
          else { setVaccine({ ...rowData }); setVaccineDialog(true); }
        }} 
      />
      <Button 
        icon="pi pi-trash" 
        className="btn-action-minimal btn-action-delete" 
        onClick={() => confirmDelete(rowData, type)} 
      />
    </div>
  );

  const renderHeader = (title: string) => (
    <div className="flex flex-wrap gap-4 align-items-center justify-content-between py-2 mb-2">
      <div className="flex align-items-center gap-2">
          <div className="w-1rem h-1rem bg-navy border-round-sm"></div>
          <h2 className="m-0 font-bold text-navy tracking-tight" style={{ fontSize: '1.25rem' }}>{title}</h2>
      </div>
      <div className="p-input-icon-left w-full md:w-30rem">
        <i className="pi pi-search text-slate-400" />
        <InputText 
          type="search" 
          value={filters.global.value || ''}
          onChange={(e) => setFilters({ ...filters, global: { value: e.target.value, matchMode: FilterMatchMode.CONTAINS } })} 
          placeholder="General search in all fields..." 
          className="w-full p-inputtext-sm border-round-xl border-slate-100 py-3 pl-5" 
        />
      </div>
    </div>
  );

  return (
    <DashboardLayout activeView={activeView} onViewChange={(v) => setActiveView(v as any)}>
      <Toast ref={toast} />
      <ConfirmDialog />
      
      {activeView === 'employees' ? (
        <div className="fade-in">
          <Toolbar className="mb-4 bg-transparent border-none p-0" left={() => (
            <Button label="Register Employee" icon="pi pi-user-plus" className="btn-corporate-primary btn-sm px-4" onClick={() => { setUser(emptyUser); setUserDialog(true); }} />
          )} right={() => (
            <Button label="Export to Excel" icon="pi pi-file-excel" className="btn-corporate-outline btn-sm" onClick={exportExcel} />
          )} />
          
          <DataTable 
            value={users} 
            loading={loading} 
            paginator 
            rows={10} 
            filters={filters}
            filterDisplay="row"
            header={() => renderHeader('Workforce Inventory')} 
            className="corporate-table"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown CurrentPageReport"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} records"
            responsiveLayout="stack" breakpoint="960px"
          >
            <Column field="identCard" header="Identification Number" sortable filter filterPlaceholder="Search ID" style={{ width: '15%' }} className="font-bold text-navy" />
            <Column header="Employee Name" body={(u) => `${u.firstName} ${u.lastName}`} sortable filter filterField="firstName" filterPlaceholder="Search Name" style={{ width: '20%' }} />
            <Column field="email" header="Corporate Email" sortable filter filterPlaceholder="Search Email" style={{ width: '25%' }} />
            <Column field="isVaccinated" header="Condition" body={statusTemplate} sortable style={{ width: '15%' }} />
            <Column header="Vaccine Brand" body={vaccineInfoTemplate} sortable style={{ width: '15%' }} />
            <Column field="numberOfDoses" header="Doses" sortable style={{ width: '5%' }} className="text-center font-bold" />
            <Column body={(u) => actionTemplate(u, 'user')} style={{ width: '5%', minWidth: '8rem' }} />
          </DataTable>
        </div>
      ) : (
        <div className="fade-in">
          <Toolbar className="mb-4 bg-transparent border-none p-0" left={() => (
            <Button label="Add Vaccine" icon="pi pi-plus-circle" className="btn-corporate-primary btn-sm px-4" onClick={() => { setVaccine(emptyVaccine); setVaccineDialog(true); }} />
          )} right={() => (
            <Button label="Export Data" icon="pi pi-file-excel" className="btn-corporate-outline btn-sm" onClick={exportExcel} />
          )} />
          
          <DataTable 
            value={vaccines} 
            loading={loading} 
            paginator 
            rows={10} 
            filters={filters}
            filterDisplay="row"
            header={() => renderHeader('Vaccine Resources')} 
            className="corporate-table"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
          >
            <Column field="name" header="Vaccine Brand" sortable filter filterPlaceholder="Search Brand" style={{ width: '50%' }} className="font-bold text-navy" />
            <Column field="type" header="Scientific Category" sortable filter filterPlaceholder="Search Category" style={{ width: '40%' }} />
            <Column body={(v) => actionTemplate(v, 'vaccine')} style={{ width: '10%', minWidth: '8rem' }} />
          </DataTable>
        </div>
      )}

      {/* --- User Dialog --- */}
      <Dialog 
        visible={userDialog} 
        style={{ width: '550px' }} 
        header="Employee Information Card" 
        modal 
        footer={() => (
          <div className="flex justify-content-end gap-2">
            <Button label="Cancel" className="p-button-text p-button-secondary p-button-sm font-bold" onClick={() => setUserDialog(false)} />
            <Button label="Save" className="p-button p-component btn-corporate-primary px-5" onClick={handleSaveUser} />
          </div>
        )} 
        onHide={() => setUserDialog(false)} 
        className="corporate-dialog"
        pt={ptDialog}
        appendTo="self"
      >
        <div className="p-fluid grid mt-3">
          <div className="field col-12">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Identification Number</label>
            <InputNumber value={user.identCard} onValueChange={(e) => setUser({...user, identCard: e.value ?? null})} useGrouping={false} placeholder="Primary identification digits" className="border-round-lg" />
          </div>
          <div className="field col-6">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">First Name</label>
            <InputText value={user.firstName} onChange={(e) => setUser({...user, firstName: e.target.value})} className="border-round-lg" />
          </div>
          <div className="field col-6">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Last Name</label>
            <InputText value={user.lastName} onChange={(e) => setUser({...user, lastName: e.target.value})} className="border-round-lg" />
          </div>
          <div className="field col-12">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Email Address</label>
            <InputText value={user.email} onChange={(e) => setUser({...user, email: e.target.value})} className="border-round-lg" />
          </div>
          
          <div className="field col-12 mt-2">
            <div className="flex align-items-center gap-3 p-3 bg-slate-50 border-round-xl border-1 border-slate-200">
               <input 
                 type="checkbox" 
                 id="vax-toggle"
                 checked={user.isVaccinated} 
                 onChange={(e) => setUser({...user, isVaccinated: e.target.checked})} 
                 style={{ width: '1.4rem', height: '1.4rem', accentColor: '#1e293b' }}
               />
               <label htmlFor="vax-toggle" className="font-bold text-navy m-0 cursor-pointer">Vaccination Protocol Verified</label>
            </div>
          </div>

          {user.isVaccinated && (
            <>
              <div className="field col-12 mt-2">
                <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Primary Manufacturer</label>
                <Dropdown value={user.vaccine} options={vaccines} optionLabel="name" onChange={(e) => setUser({...user, vaccine: e.value})} placeholder="Select brand" className="border-round-lg" />
              </div>
              <div className="field col-12">
                <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Cumulative Doses Received</label>
                <InputNumber value={user.numberOfDoses} onValueChange={(e) => setUser({...user, numberOfDoses: e.value ?? 0})} showButtons min={0} className="border-round-lg" />
              </div>
            </>
          )}
        </div>
      </Dialog>

      {/* --- Vaccine Dialog --- */}
      <Dialog 
        visible={vaccineDialog} 
        style={{ width: '450px' }} 
        header="Vaccine Catalog Configuration" 
        modal 
        footer={() => (
          <div className="flex justify-content-end gap-2">
            <Button label="Cancel" className="p-button-text p-button-secondary p-button-sm font-bold" onClick={() => setVaccineDialog(false)} />
            <Button label="Save" className="p-button p-component btn-corporate-primary px-5" onClick={handleSaveVaccine} />
          </div>
        )} 
        onHide={() => setVaccineDialog(false)} 
        className="corporate-dialog"
        pt={ptDialog}
        appendTo="self"
      >
        <div className="p-fluid grid mt-3">
          <div className="field col-12">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Vaccine Brand Name</label>
            <InputText value={vaccine.name} onChange={(e) => setVaccine({...vaccine, name: e.target.value})} className="border-round-lg" />
          </div>
          <div className="field col-12">
            <label className="font-bold text-xs text-slate-500 uppercase tracking-widest pl-1 mb-2 block">Technology Category</label>
            <InputText value={vaccine.type} onChange={(e) => setVaccine({...vaccine, type: e.target.value})} placeholder="e.g. mRNA, Viral Vector..." className="border-round-lg" />
          </div>
        </div>
      </Dialog>
    </DashboardLayout>
  );
}
