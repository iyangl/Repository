 /**
     * 获取图片类型
     */
    public int getType(byte[] data) {
        // Png test:  
        if (data[1] == 'P' && data[2] == 'N' && data[3] == 'G') {
            return TYPE_PNG;
        }
        // Gif test:  
        if (data[0] == 'G' && data[1] == 'I' && data[2] == 'F') {
            return TYPE_GIF;
        }
        // JPG test:  
        if (data[6] == 'J' && data[7] == 'F' && data[8] == 'I'
                && data[9] == 'F') {
            return TYPE_JPG;
        }
        return TYPE_JPG;
    }