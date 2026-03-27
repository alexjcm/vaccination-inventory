export interface Breakpoints {
  greaterThanMobile: string;
}

export const bps: Breakpoints = {
  greaterThanMobile: '@media screen and (min-width: 55rem)'
};

export interface ThemeColors {
  textColor: string;
  primary: string;
}

export interface ThemePaddings {
  xs: string;
  s: string;
  m: string;
  l: string;
}

export interface Theme {
  colors: ThemeColors;
  fontSizes: Record<string, string>;
  paddings: ThemePaddings;
}

export const theme: Theme = {
  colors: {
    textColor: 'var(--theme-body-txt)',
    primary: 'var(--brand-color_3)'
  },
  fontSizes: {},
  paddings: {
    xs: '4px',
    s: '8px',
    m: '16px',
    l: '32px'
  }
};