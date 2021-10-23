import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import React, { useState, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import { InputNumber } from 'primereact/inputnumber';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import './DataTableDemo.css';
//import { UserService } from '../service/UserService';
import useUsers from '../hooks/useUsers';

export default function Users() {

    let emptyUser = {
        id: null,
        identCard: '',
        firstName: '',
    };

    const [user, setUser] = useState(emptyUser);
    //const [users, setUsers] = useState(null);
    const [userDialog, setUserDialog] = useState(false);
    const [deleteUserDialog, setDeleteUserDialog] = useState(false);
    const [deleteUsersDialog, setDeleteUsersDialog] = useState(false);    
    const [selectedUsers, setSelectedUsers] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const toast = useRef(null);
    const dt = useRef(null);//////////////
    //const userService = new UserService();//////////
    let token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0b21AYWNtZS5jb20iLCJuYmYiOjE2MzM5NzkxMDcsInJvbGVzIjoiUk9MRV9BRE1JTiIsImlzcyI6IkN1c3RvbUlzc3VlciIsImV4cCI6MTYzMzk4MjcwNywiaWF0IjoxNjMzOTc5MTA3LCJ1c2VybmFtZSI6InRvbUBhY21lLmNvbSJ9.S2PiJWG1Nmewi2Wkawukw2HSg8El8bOew6Or2Nuof44D4WnBQPnP4C_mqI2WIoDavcPRIT7sG5PASdT6vXfwFWXlmfiaCheiM1el1Spu4HvBrMlKg7uSILvnUVAK4ndOfOS0YYgTunpul4v29yKhEEAaOSB3Nklcn4sYAj8Ix_Hu2dZeKjFFCbXhJaHy8woKhhm5WJa-j8xgK-qctGv8JsG8Yj4wcawaTaM6CH9NYMf9enwOban3dcR-oyzCJI3wvwgv6ATKq34SXHzWI09ZD-KN56-xwETABS3HOEb8s-VFRFOo3mF3suzpBSrCZqT65AEtNQVFSYOxKJTcm940IQ"
    const {loading, users} = useUsers({token});

    const openNew = () => {
        setUser(emptyUser);
        setSubmitted(false);
        setUserDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setUserDialog(false);
    }

    const hideDeleteUserDialog = () => {
        setDeleteUserDialog(false);
    }

    const hideDeleteUsersDialog = () => {
        setDeleteUsersDialog(false);
    }

    const saveUser = () => {
        setSubmitted(true);

        if (user.firstName.trim()) {
            let _users = [...users];
            let _user = {...user};
            if (user.id) {
                const index = findIndexById(user.id);

                _users[index] = _user;
                toast.current.show({ severity: 'success', summary: 'Successful', detail: 'User Updated', life: 3000 });
            }
            else {           
                _user.image = 'user-placeholder.svg';
                _users.push(_user);
                toast.current.show({ severity: 'success', summary: 'Successful', detail: 'User Created', life: 3000 });
            }

            //setUsers(_users);////////////////////
            setUserDialog(false);
            setUser(emptyUser);
        }
    }

    const editUser = (user) => {
        setUser({...user});
        setUserDialog(true);
    }

    const confirmDeleteUser = (user) => {
        setUser(user);
        setDeleteUserDialog(true);
    }

    const deleteUser = () => {
        let _users = users.filter(val => val.id !== user.id);
        setUser(_users);
        setDeleteUserDialog(false);
        setUser(emptyUser);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000 });
    }

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < users.length; i++) {
            if (users[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    }
 
    const confirmDeleteSelected = () => {
        setDeleteUsersDialog(true);
    }

    const deleteSelectedUsers = () => {
        let _users = users.filter(val => !selectedUsers.includes(val));
        //setUsers(_users);//////////////
        setDeleteUsersDialog(false);
        setSelectedUsers(null);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'Users Deleted', life: 3000 });
    }

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _user = {...user};
        _user[`${name}`] = val;

        setUser(_user);
    }

    const onInputNumberChange = (e, name) => {
        const val = e.value || 0;
        let _user = {...user};
        _user[`${name}`] = val;

        setUser(_user);
    }

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button label="New" icon="pi pi-plus" className="p-button-success p-mr-2" onClick={openNew} />
                <Button label="Delete" icon="pi pi-trash" className="p-button-danger" onClick={confirmDeleteSelected} disabled={!selectedUsers || !selectedUsers.length} />
            </React.Fragment>
        )
    }
  
    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success p-mr-2" onClick={() => editUser(rowData)} />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-warning" onClick={() => confirmDeleteUser(rowData)} />
            </React.Fragment>
        );
    }

    const header = (
        <div className="table-header">
            <h5 className="p-m-0">Manage Users</h5>
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Search..." />
            </span>
        </div>
    );
    const userDialogFooter = (
        <React.Fragment>
            <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveUser} />
        </React.Fragment>
    );
    const deleteUserDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteUserDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteUser} />
        </React.Fragment>
    );
    const deleteUsersDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteUsersDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteSelectedUsers} />
        </React.Fragment>
    );

    return (
        <div className="datatable-crud-demo">
            <Toast ref={toast} />

            <div className="card">
                <Toolbar className="p-mb-4" left={leftToolbarTemplate}></Toolbar>

                <DataTable ref={dt} value={users} selection={selectedUsers} onSelectionChange={(e) => setSelectedUsers(e.value)}
                    dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} users"
                    globalFilter={globalFilter}
                    header={header}>

                    <Column selectionMode="multiple" headerStyle={{ width: '3rem' }}></Column>
                    <Column field="id" header="Id" sortable></Column>
                    <Column field="firstName" header="firstName" sortable></Column>          
                   
                  <Column body={actionBodyTemplate}></Column>
                </DataTable>
            </div>

            <Dialog visible={userDialog} style={{ width: '450px' }} header="User Details" modal className="p-fluid" footer={userDialogFooter} onHide={hideDialog}>
                <div className="p-field">
                    <label htmlFor="firstName">firstName</label>
                    <InputText id="firstName" value={user.firstName} onChange={(e) => onInputChange(e, 'firstName')} required autoFocus className={classNames({ 'p-invalid': submitted && !user.firstName })} />
                    {submitted && !user.firstName && <small className="p-error">firstName is required.</small>}
                </div>
                <div className="p-field">
                    <label htmlFor="identCard">identCard</label>                 
                    <InputNumber id="identCard"value={user.identCard} onValueChange={(e) =>  onInputNumberChange(e, 'identCard')} required integeronly />
                </div>          


            </Dialog>

            <Dialog visible={deleteUserDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteUserDialogFooter} onHide={hideDeleteUserDialog}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem'}} />
                    {user && <span>Are you sure you want to delete <b>{user.firstName}</b>?</span>}
                </div>
            </Dialog>

            <Dialog visible={deleteUsersDialog} style={{ width: '450px' }} header="Confirm" modal footer={deleteUsersDialogFooter} onHide={hideDeleteUsersDialog}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem'}} />
                    {user && <span>Are you sure you want to delete the selected users?</span>}
                </div>
            </Dialog>
        </div>
    );
}
