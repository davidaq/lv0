function L(text) {
    if(CFG.dict[text])
        return CFG.dict[text];
    return text.replace(/_/g, ' ');
}
