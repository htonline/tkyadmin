<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">热修复状态</label>
        <el-input v-model="query.state" clearable placeholder="热修复状态" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">热修复差分包</label>
        <el-input v-model="query.filepath" clearable placeholder="热修复差分包" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">标记</label>
        <el-input v-model="query.id" clearable placeholder="标记" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="热修复状态" prop="state">
            <el-input v-model="form.state" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="热修复差分包">
            <el-input v-model="form.filepath" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="标记" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="state" label="热修复状态" />
        <el-table-column prop="filepath" label="热修复差分包" />
        <el-table-column prop="id" label="标记" />
        <el-table-column v-if="checkPer(['admin','hotfix:edit','hotfix:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudHotfix from '@/api/hotfix'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { state: null, filepath: null, id: null }
export default {
  name: 'Hotfix',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/hotfix', url: 'api/hotfix', idField: 'id', sort: 'id,desc', crudMethod: { ...crudHotfix }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'hotfix:add'],
        edit: ['admin', 'hotfix:edit'],
        del: ['admin', 'hotfix:del']
      },
      rules: {
        state: [
          { required: true, message: '热修复状态不能为空', trigger: 'blur' }
        ],
        id: [
          { required: true, message: '标记不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'state', display_name: '热修复状态' },
        { key: 'filepath', display_name: '热修复差分包' },
        { key: 'id', display_name: '标记' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
