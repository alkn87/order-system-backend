import { GlobalConfig, IndividualConfig } from 'ngx-toastr';

export const defaultToastConfig: IndividualConfig = {
  payload: undefined,
  newestOnTop: true,
  disableTimeOut: false,
  timeOut: 7500,
  closeButton: true,
  extendedTimeOut: 1000,
  progressBar: false,
  progressAnimation: 'decreasing',
  enableHtml: true,
  toastClass: 'ngx-toastr',
  positionClass: 'toast-top-center',
  titleClass: 'toast-title',
  messageClass: 'toast-message',
  easing: 'ease-in',
  easeTime: 300,
  tapToDismiss: true,
  onActivateTick: false
};

export const globalToastConfig: GlobalConfig = {
  includeTitleDuplicates: false,
  ...defaultToastConfig,
  maxOpened: 0,
  autoDismiss: false,
  iconClasses: {error: 'toast-error'},
  preventDuplicates: true,
  countDuplicates: false,
  resetTimeoutOnDuplicate: false
};
