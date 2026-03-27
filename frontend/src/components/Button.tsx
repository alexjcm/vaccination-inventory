import React, {ReactNode} from 'react';
import {Button, Link} from './styles';

interface ButtonComponentProps {
  children: ReactNode;
  href?: string;
  size?: 'small' | 'medium' | 'large';
}

export default function ButtonComponent({
  children,
  href,
  size = 'small',
}: ButtonComponentProps) {
  return href ? (
    <Link size={size} href={href}>
      {children}
    </Link>
  ) : (
    <Button size={size}>{children}</Button>
  );
}
