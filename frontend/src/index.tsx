import React from 'react';
import { createRoot } from 'react-dom/client';

import { ThemeProvider } from '@emotion/react';
import { theme } from './styles/styles';

import App from './App';
import './index.css';

import { PrimeReactProvider } from 'primereact/api';

const container = document.getElementById('root');
if (!container) {
    console.error('Failed to find the root element');
    throw new Error('Failed to find the root element');
}

console.log('Mounting React app to root container');
const root = createRoot(container);

root.render(
  <React.StrictMode>
    <PrimeReactProvider value={{ ripple: true }}>
      <App />
    </PrimeReactProvider>
  </React.StrictMode>
);
