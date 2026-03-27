import {Link as LinkWouter} from 'wouter';
import styled from '@emotion/styled';
import { Theme } from '../styles/styles';
import { css } from '@emotion/react';

const SIZES = {
  small: '1rem',
  medium: '2rem',
  large: '3rem',
} as const;

type SizeKey = keyof typeof SIZES;

interface StyledProps {
  size: SizeKey;
  theme?: Theme;
}

const getFontSize = (props: StyledProps) => {
  return SIZES[props.size] || SIZES.small;
};

const commonStyles = (props: StyledProps) => css`
  background-color: ${props.theme?.colors.primary};
  border: 1px solid transparent;
  color: ${props.theme?.colors.textColor};
  cursor: pointer;
  font-size: ${getFontSize(props)};
  padding: 0.5rem 1rem;

  :hover {
    background-color: var(--brand-color_2);
  }

  &[disabled] {
    opacity: 0.3;
    pointer-events: none;
  }
`;

export const Link = styled(LinkWouter)<StyledProps>`
  ${commonStyles}
`;

export const Button = styled.button<StyledProps>`
  ${commonStyles}
`;
