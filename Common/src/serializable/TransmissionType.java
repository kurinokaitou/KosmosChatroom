package serializable;

public enum TransmissionType {
    LOGIN,          // 登录
    LOGOUT,         // 登出
    REGISTER,       // 注册
    CHAT,           // 私聊
    GROUP_CHAT,     // 群聊
    SEARCH,         // 查找用户
    SEARCH_GROUP,   // 查找群组
    CREATE_GROUP,   // 创建群组
    INIT,           // 初始化客户端
    RETENT,         // 获取未读信息
}
