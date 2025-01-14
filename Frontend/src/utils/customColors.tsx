import { createTheme } from '@mui/material/styles';

declare module "@mui/material/styles" {
  interface Palette {
    customButtonColor: string;
  }
  interface PaletteOptions {
    customButtonColor: string;
  }
}

declare module "@mui/material/Button" {
  interface ButtonPropsColorOverrides {
    customButtonColor: true;
  }
}

const { palette } = createTheme();

export const loginTheme = createTheme({
  palette: {
    customButtonColor: palette.augmentColor({ color: {
        main: '#415a77'
      }
    }),
  }
});