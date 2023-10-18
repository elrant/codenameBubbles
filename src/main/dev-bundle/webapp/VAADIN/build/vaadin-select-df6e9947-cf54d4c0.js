import{R as e,p as l,O as t,k as o,m as s,x as i}from"./indexhtml-251932fc.js";import{labelProperties as p,helperTextProperties as d,errorMessageProperties as n}from"./vaadin-text-field-0b3db014-0b0258d8.js";const v={tagName:"vaadin-select",displayName:"Select",elements:[{selector:"vaadin-select::part(input-field)",displayName:"Field",properties:[e.backgroundColor,e.borderColor,e.borderWidth,e.borderRadius,l.height,l.paddingInline]},{selector:"vaadin-select vaadin-select-value-button>vaadin-select-item",displayName:"Field text",properties:[t.textColor,t.fontSize,t.fontWeight]},{selector:"vaadin-select::part(toggle-button)",displayName:"Field toggle button",properties:[o.iconColor,o.iconSize]},{selector:"vaadin-select::part(label)",displayName:"Label",properties:p},{selector:"vaadin-select::part(helper-text)",displayName:"Helper text",properties:d},{selector:"vaadin-select::part(error-message)",displayName:"Error message",properties:n},{selector:"vaadin-select-overlay::part(overlay)",displayName:"Overlay",properties:[e.backgroundColor,e.borderColor,e.borderWidth,e.borderRadius,e.padding]},{selector:"vaadin-select-overlay vaadin-select-item",displayName:"Overlay items",properties:[t.textColor,t.fontSize,t.fontWeight]},{selector:"vaadin-select-overlay vaadin-select-item::part(checkmark)::before",displayName:"Overlay item checkmark",properties:[o.iconColor,o.iconSize]}],async setupElement(r){r.overlayClass=r.getAttribute("class")},openOverlay:r=>{const a=r.element;s(a,a,a)},hideOverlay:r=>{const a=r.element;a.opened=!1,i(a,a,a)}};export{v as default};
